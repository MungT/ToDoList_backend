package sparta.seed.login.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
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
//import sparta.seed.repository.RefreshTokenRepository;
import sparta.seed.login.repository.RefreshTokenRepository;
import sparta.seed.sercurity.UserDetailsImpl;

import javax.servlet.http.HttpServletResponse;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoUserService {
  private final PasswordEncoder passwordEncoder;
  private final TokenProvider tokenProvider;
  private final MemberRepository memberRepository;
  private final RefreshTokenRepository refreshTokenRepository;




  public MemberResponseDto kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException {
    // 1. "인가 코드"로 "액세스 토큰" 요청
    System.out.println("카카오 로그인 1번 접근");
    String accessToken = getAccessToken(code);
    // 2. 토큰으로 카카오 API 호출
    System.out.println("카카오 로그인 2번 접근");
    SocialMemberRequestDto kakaoUserInfo = getKakaoUserInfo(accessToken);
    // 3. 필요시에 회원가입
    System.out.println("카카오 로그인 3번 접근");
    Member kakaoUser = registerKakaoUserIfNeeded(kakaoUserInfo);
    // 4. 강제 로그인 처리
    System.out.println("카카오 로그인 4번 접근");
    return forceLogin(kakaoUser,response);
  }

  private String getAccessToken(String code) throws JsonProcessingException {
    System.out.println(code);
    // HTTP Header 생성
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
    // HTTP Body 생성
    //받은 코드를 헤더랑 바디에 세팅해서 http메세지로 만들어서 카카오로 보낸다음에 ㅔ우리가 그 토큰을 받아서 어떻게 핸들링한다 ㅣㅇ거여
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "authorization_code");
    body.add("client_id", "7961d1dae4bcc3e0b41dac5ca7150775");
    body.add("redirect_uri", "http://localhost:3000/user/kakao/callback");
    body.add("code", code);
    /**
     * 받은 인가코드로 카카오에 엑세스토큰 요청
     */
    // HTTP 요청 보내기
    HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
    RestTemplate rt = new RestTemplate();
    ResponseEntity<String> response = rt.exchange(
            "https://kauth.kakao.com/oauth/token",
            HttpMethod.POST,
            kakaoTokenRequest,
            String.class
    );

    /**
     * 토큰 파싱해서 리턴
     */
    // HTTP 응답 (JSON) -> 액세스 토큰 파싱
    String responseBody = response.getBody();
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.readTree(responseBody);
    return jsonNode.get("access_token").asText();
  }

  private SocialMemberRequestDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
    // HTTP Header 생성
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + accessToken);
    headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
    /**
     * 리턴받은 토큰으로 카카오에 유저정보 요청
     */
    // HTTP 요청 보내기
    HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
    RestTemplate rt = new RestTemplate();
    ResponseEntity<String> response = rt.exchange(
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.POST,
            kakaoUserInfoRequest,
            String.class
    );
    /**
     * 유저정보 파싱해서 리턴
     */
    String responseBody = response.getBody();
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.readTree(responseBody);

    Random rnd = new Random();
    String rdNick="";
    for (int i = 0; i < 8; i++) {
      rdNick += String.valueOf(rnd.nextInt(10));
    }

    String id = jsonNode.get("id").toString();
    String nickname = "K" + "_" + rdNick;
    String username = jsonNode.get("kakao_account").get("email").asText();
    String defaultImage = "https://mytest-coffick.s3.ap-northeast-2.amazonaws.com/coffindBasicImage.png";

    return SocialMemberRequestDto.builder()
            .socialId(id)
            .username(username)
            .nickname(nickname)
            .profileImage(defaultImage)
            .build();
  }

  private Member registerKakaoUserIfNeeded(SocialMemberRequestDto kakaoUserInfo) {
    /**
     *  응답받은 유저정보를 토대로 나의 DB에 같은 유저가 가입되어 있는지 확인 후 없다면 DB에 정보 저장 아니면 그대로 리턴
     */
    // DB 에 중복된 Kakao Id 가 있는지 확인
    String socialId = kakaoUserInfo.getSocialId();
    Member member = memberRepository.findBySocialId(socialId).orElse(null);
    if (member == null) {      // 회원가입
      String username = kakaoUserInfo.getUsername();
      String nickname = kakaoUserInfo.getNickname();
      String password = passwordEncoder.encode(UUID.randomUUID().toString());
      String profileImage = kakaoUserInfo.getProfileImage();
      String highschool = kakaoUserInfo.getHighschool();
      String grade = kakaoUserInfo.getGrade();

      Member signUp = Member.builder()
              .socialId(socialId)
              .username(username)
              .nickname(nickname)
              .password(password)
              .profileImage(profileImage)
              .highschool(highschool)
              .grade(grade)
              .authority(Authority.ROLE_USER)
              .build();
      return memberRepository.save(signUp);
    }
    return member;
  }

  private MemberResponseDto forceLogin(Member kakaoUser,HttpServletResponse response) {
    /**
     *  가입된 유저의 정보를 받아서 authentication 객체를 생성해서 프론트와 통신할 유효한 토큰을 생성하고 SecurityContextHolder에
     *  authentication 객체를 SET 해서 강제로 로그인처리.
     */
    UserDetailsImpl member = new UserDetailsImpl(kakaoUser);
    Authentication authentication = new UsernamePasswordAuthenticationToken(member, null, member.getAuthorities());
    //토큰생성
    MemberResponseDto memberResponseDto = tokenProvider.generateTokenDto(authentication);
    response.setHeader("Authorization", "Bearer " + memberResponseDto.getAccessToken());
    response.setHeader("Access-Token-Expire-Time", String.valueOf(memberResponseDto.getAccessTokenExpiresIn()));

//    RefreshToken refreshToken = RefreshToken.builder()
//            .refreshKey(authentication.getName())
//            .refreshValue(memberResponseDto.getRefreshToken())
//            .build();
//
//    refreshTokenRepository.save(refreshToken);
    //로그인이 실제로 일어나는 부분
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return MemberResponseDto.builder()
            .id(member.getId())
            .username(member.getUsername())
            .nickname(member.getNickname())
            .accessToken(memberResponseDto.getAccessToken())
            .accessTokenExpiresIn(memberResponseDto.getAccessTokenExpiresIn())
            .profileImage(memberResponseDto.getProfileImage())
            .grantType(memberResponseDto.getGrantType())
            .refreshToken(memberResponseDto.getRefreshToken())
            .build();
  }
}