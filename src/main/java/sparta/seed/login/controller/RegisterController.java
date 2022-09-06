package sparta.seed.login.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import sparta.seed.login.domain.Member;
import sparta.seed.login.dto.*;
import sparta.seed.login.repository.MemberRepository;
import sparta.seed.login.service.MemberService;
import sparta.seed.message.Message;
import sparta.seed.sercurity.UserDetailsImpl;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class RegisterController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @PostMapping("/api/check-nickname")
    public ResponseEntity<String> checkNickname(@Valid @RequestBody NicknameRequestDto nicknameRequestDto){
        return ResponseEntity.ok()
        .body( memberService.checkNickname(nicknameRequestDto.getNickname()));
    }

    @PostMapping("/api/signup")
    public ResponseEntity<Member> signup(@RequestBody SocialMemberRequestDto socialMemberRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return ResponseEntity.ok()
                .body(memberService.signup(socialMemberRequestDto,userDetailsImpl));
    }
    @PostMapping("/reissue")  //재발급을 위한 로직
    public ResponseEntity<MemberResponseDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(memberService.reissue(tokenRequestDto));
    }
}
