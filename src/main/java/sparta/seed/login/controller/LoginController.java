package sparta.seed.login.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sparta.seed.login.dto.MemberResponseDto;
import sparta.seed.login.service.GoogleUserService;
import sparta.seed.login.service.KakaoUserService;
import sparta.seed.login.service.NaverUserService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class LoginController {
  private final GoogleUserService googleUserService;
  private final KakaoUserService kakaoUserService;
  private final NaverUserService naverUserService;

  @GetMapping("/user/kakao/callback")
  public MemberResponseDto kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
    System.out.println(code);
    return kakaoUserService.kakaoLogin(code, response);
  }

  @GetMapping("/user/google/callback")
  public MemberResponseDto googleLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
    return googleUserService.googleLogin(code, response);
  }

  @GetMapping("/user/naver/callback")
  public MemberResponseDto naverLogin(@RequestParam String code, @RequestParam String state, HttpServletResponse response) throws JsonProcessingException {
    return naverUserService.naverLogin(code, state, response);
  }
}
