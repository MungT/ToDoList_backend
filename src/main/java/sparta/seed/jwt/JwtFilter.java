package sparta.seed.jwt;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import sparta.seed.exception.Code;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

   public static final String AUTHORIZATION_HEADER = "Authorization";
   public static final String BEARER_PREFIX = "Bearer ";

   private final TokenProvider tokenProvider;

   // 실제 필터링 로직은 doFilterInternal 에 들어감
   // JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext 에 저장하는 역할 수행
   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
      // 1. Request Header 에서 토큰을 꺼냄
      String jwt = resolveToken(request);
      if(jwt == null){
         request.setAttribute("exception", Code.UNKNOWN_ERROR.getCode());
      }

      // 2. validateToken 으로 토큰 유효성 검사
      // 정상 토큰이면 해당 토큰으로 Authentication 을 가져와서 SecurityContext 에 저장
      try {
         System.out.println("토큰 유효성 검사");
         if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
         }
      } catch (ExpiredJwtException e){
         //만료 에러
         request.setAttribute("exception", Code.EXPIRED_TOKEN.getCode());
      } catch (MalformedJwtException e){
         //변조 에러
         request.setAttribute("exception", Code.WRONG_TYPE_TOKEN.getCode());
      } catch (SignatureException e){
         //형식, 길이 에러
         request.setAttribute("exception", Code.WRONG_TYPE_TOKEN.getCode());
      } catch(JwtException e){
         request.setAttribute("exception", Code.UNKNOWN_ERROR);
      }

      filterChain.doFilter(request, response);
   }

   // Request Header 에서 토큰 정보를 꺼내오기
   private String resolveToken(HttpServletRequest request) {
      String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
      if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
         return bearerToken.substring(7);
      }
      return null;
   }
}