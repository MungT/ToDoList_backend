package sparta.seed.login.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.util.StandardCharset;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sparta.seed.login.domain.Authority;
import sparta.seed.login.domain.Member;
import sparta.seed.login.domain.RefreshToken;
import sparta.seed.login.dto.SocialMemberRequestDto;
import sparta.seed.login.dto.MemberResponseDto;
import sparta.seed.jwt.TokenProvider;
import sparta.seed.login.repository.MemberRepository;
import sparta.seed.login.repository.RefreshTokenRepository;
import sparta.seed.sercurity.UserDetailsImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class GoogleUserService {

  @Value("${spring.security.oauth2.client.registration.google.client-id}")
  String googleClientId;
  @Value("${spring.security.oauth2.client.registration.google.client-secret}")
  String googleClientSecret;
  @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
  String googleRedirectUri;
  private final MemberRepository memberRepository;
  private final TokenProvider tokenProvider;
  private final RefreshTokenRepository refreshTokenRepository;

  public MemberResponseDto googleLogin(String code, HttpServletResponse response) throws JsonProcessingException {

    System.out.println("구글 로그인 1번 접근");
    String accessToken = getAccessToken(code);

//   2. 엑세스토큰으로 유저정보 가져오기
    System.out.println("구글 로그인 2번 접근");
    SocialMemberRequestDto googleUserInfo = getGoogleUserInfo(accessToken);

    // 3. 유저확인 & 회원가입
    System.out.println("구글 로그인 3번 접근");
    Member foundUser = getUser(googleUserInfo);

    // 4. 시큐리티 강제 로그인
    System.out.println("구글 로그인 4번 접근");
    Authentication authentication = securityLogin(foundUser);

    // 5. jwt 토큰 발급
    System.out.println("구글 로그인 5번 접근");
    return jwtToken(authentication, response);
  }


  // 1. 인가코드로 엑세스토큰 가져오기
  private String getAccessToken(String code) throws JsonProcessingException {

    // 헤더에 Content-type 지정
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
    //code 출력
    System.out.println(code);
//    String decodedCode = "";
//    try {
//      decodedCode = java.net.URLDecoder.decode(code, StandardCharsets.UTF_8.name());
//    } catch (UnsupportedEncodingException e) {
//      throw new RuntimeException(e);
//    }
//    System.out.println(decodedCode);
    // 바디에 필요한 정보 담기
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("client_id", googleClientId);
    body.add("client_secret", googleClientSecret);
    body.add("code", code);
    body.add("redirect_uri", googleRedirectUri);
    body.add("grant_type", "authorization_code");

    // POST 요청 보내기
    HttpEntity<MultiValueMap<String, String>> googleToken = new HttpEntity<>(body, headers);
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> response = restTemplate.exchange(
            "https://oauth2.googleapis.com/token",
            HttpMethod.POST,
            googleToken,
            String.class
    );

    // response에서 accessToken 가져오기
    String responseBody = response.getBody();
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode responseToken = objectMapper.readTree(responseBody);
    return responseToken.get("access_token").asText();
  }

  // 2. 엑세스토큰으로 유저정보 가져오기
  private SocialMemberRequestDto getGoogleUserInfo(String accessToken) throws JsonProcessingException {
    // 헤더에 엑세스토큰 담기, Content-type 지정
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + accessToken);
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    // POST 요청 보내기
    HttpEntity<MultiValueMap<String, String>> googleUser = new HttpEntity<>(headers);
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> response = restTemplate.exchange(
            "https://openidconnect.googleapis.com/v1/userinfo",
            HttpMethod.POST, googleUser,
            String.class
    );

    // response에서 유저정보 가져오기
    String responseBody = response.getBody();
    ObjectMapper objectMapper = new ObjectMapper();

    JsonNode jsonNode = objectMapper.readTree(responseBody);

    System.out.println(jsonNode);

    String socialId = jsonNode.get("sub").asText();
    String userEmail = jsonNode.get("email").asText();
    String nickname = jsonNode.get("name").asText();

    // 구글에서 이미지 가져오기
//    String profileImage = jsonNode.get("picture").asText();
    String defaultImage = "https://ibb.co/9Tvy0WP";
//    if (profileImage == null) {
//      profileImage = defaultImage;
//    }
    return SocialMemberRequestDto.builder()
            .socialId(socialId)
            .username(userEmail)
            .nickname(nickname)
            .profileImage(defaultImage)
            .build();
  }

  private Member getUser(SocialMemberRequestDto requestDto) {
    // 다른 소셜로그인이랑 이메일이 겹쳐서 잘못 로그인 될까봐. 다른 사용자인줄 알고 로그인이 된다. 그래서 소셜아이디로 구분해보자
    String username = requestDto.getUsername();
    String googleSocialID = requestDto.getSocialId();
    Member googleUser = memberRepository.findBySocialId(googleSocialID).orElse(null);

    if (googleUser == null) {  // 회원가입
      String socialId = requestDto.getSocialId();
      String password = UUID.randomUUID().toString();
      String profileImage = requestDto.getProfileImage();

      Member signUpMember = Member.builder()
              .username(username)
              .password(password)
              .profileImage(profileImage)
              .socialId(socialId)
              .authority(Authority.ROLE_USER)
//              .nickname(requestDto.getNickname()) // nickname을 null로 하기위해
              .build();
      memberRepository.save(signUpMember);
      return signUpMember;
    }
    return googleUser;
  }

  // 4. 시큐리티 강제 로그인
  private Authentication securityLogin(Member findUser) {
    UserDetails userDetails = new UserDetailsImpl(findUser);
    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
    //여기까진 평범한 로그인과 같음, 구글 강제로그인 시도까지 함
    return authentication;
  }

  // 5. jwt 토큰 발급
  private MemberResponseDto jwtToken(Authentication authentication, HttpServletResponse response) {
    //여기부터 토큰 프론트에 넘기는것
    UserDetailsImpl member = ((UserDetailsImpl) authentication.getPrincipal());
    MemberResponseDto responseDto = tokenProvider.generateTokenDto(authentication);
    String token = responseDto.getAccessToken();
    response.addHeader("Authorization", "Bearer " + token);

    RefreshToken refreshToken = RefreshToken.builder()
            .key(authentication.getName())
            .value(responseDto.getRefreshToken())
            .build();

    refreshTokenRepository.save(refreshToken);

    return MemberResponseDto.builder()
            .id(member.getId())
            .username(member.getUsername())
            .nickname(member.getNickname())
            .accessToken(responseDto.getAccessToken())
            .accessTokenExpiresIn(responseDto.getAccessTokenExpiresIn())
            .grantType(responseDto.getGrantType())
            .refreshToken(responseDto.getRefreshToken())
            .build();
  }

}
