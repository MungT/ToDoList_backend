package sparta.seed.login.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import sparta.seed.login.domain.Authority;
import sparta.seed.login.domain.Member;
import sparta.seed.login.dto.MemberResponseDto;
import sparta.seed.sercurity.UserDetailsImpl;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 2;            // 2시간
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;  // 7일
    private static final String MEMBER_USERNAME = "memberUsername";
    private static final String MEMBER_NICKNAME = "memberNickname";
    private static final String MEMBER_SOCIALID = "memberSocialid";
    private static final String MEMBER_PROFILE_IMAGE = "profileImage";
    private Authority authority;


    private final Key key;

    public TokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public MemberResponseDto generateTokenDto(Authentication authentication) {
        // 권한들 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        UserDetailsImpl userDetails = (UserDetailsImpl)authentication.getPrincipal();
        Member member = userDetails.getMember();

        long now = (new Date()).getTime();



        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setSubject(String.valueOf(member.getId()))  // 유저 index
                .claim(AUTHORITIES_KEY, authorities)        // 유저 권한정보
                .claim(MEMBER_USERNAME,member.getUsername()) //유저 이메일
                .claim(MEMBER_NICKNAME,member.getNickname()) //유저 닉네임
                .claim(MEMBER_SOCIALID,member.getSocialId())
                .claim(MEMBER_PROFILE_IMAGE,member.getProfileImage())
                .setExpiration(accessTokenExpiresIn)        // payload "exp": 1516239022 (예시)
                .signWith(key, SignatureAlgorithm.HS512)    // header "alg": "HS512"
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .setHeaderParam("JWT_HEADER_PARAM_TYPE","headerType")
                .compact();

        return MemberResponseDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .username(authentication.getName())
                .build();
    }

    // accessToken에서 인증 정보 조회
    // 토큰에 담겨 있는 정보를 이용해 Authentication 객체를 리턴하는 메소드이다.
    // createToken 과 정확히 반대의 역할을 해주는 메소드이다.
    // 토큰을 parameter 로 받아서 토큰으로 claim 을 만들고, 최종적으로는 Authentication 객체를 리턴한다.
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);
        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                                                                .map(SimpleGrantedAuthority::new)
                                                                .collect(Collectors.toList());

        if(Authority.ROLE_USER.toString().equals(claims.get(AUTHORITIES_KEY))){
            authority = Authority.ROLE_USER;
        } else {
            authority = Authority.ROLE_ADMIN;
        }

        Member member = Member.builder()
                .username((String) claims.get(MEMBER_USERNAME))
                .nickname((String) claims.get(MEMBER_NICKNAME))
                .socialId(claims.get(MEMBER_SOCIALID).toString())
                .profileImage(claims.get(MEMBER_PROFILE_IMAGE).toString())
                .authority(authority)
                .id(Long.valueOf(claims.getSubject()))
                .build();
        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetailsImpl principal = new UserDetailsImpl(member);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new MalformedJwtException("잘못된 JWT 서명입니다.");
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("JWT 토큰이 잘못되었습니다.");
        } catch (ExpiredJwtException e){
            throw new ExpiredJwtException(Jwts.header(),Jwts.claims(),"만료된 토큰입니다");
        }
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}