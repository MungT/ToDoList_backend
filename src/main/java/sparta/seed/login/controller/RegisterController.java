package sparta.seed.login.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.seed.image.dto.MottoRequestDto;
import sparta.seed.login.domain.Member;
import sparta.seed.login.dto.*;
import sparta.seed.login.repository.MemberRepository;
import sparta.seed.login.service.MemberService;
import sparta.seed.sercurity.UserDetailsImpl;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class RegisterController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    //닉네임 중복체크
    @PostMapping("/api/check-nickname")
    public ResponseEntity<String> checkNickname(@Valid @RequestBody NicknameRequestDto nicknameRequestDto){
        return ResponseEntity.ok()
        .body( memberService.checkNickname(nicknameRequestDto.getNickname()));
    }
    //닉네임,고등학교,학년 등록
    @PostMapping("/api/signup")
    public ResponseEntity<Member> signup(@RequestBody SocialMemberRequestDto socialMemberRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return ResponseEntity.ok()
                .body(memberService.signup(socialMemberRequestDto,userDetailsImpl));
    }
    //유저 정보 가져오기
    @GetMapping("/api/member/{nickname}")
    public ResponseEntity<Member> getMember(@PathVariable String nickname){
        return ResponseEntity.ok()
                .body(memberService.getMember(nickname));
    }
    //좌우명 등록
    @PostMapping("/api/motto")
    public ResponseEntity<String> updateMotto(@RequestBody MottoRequestDto mottoRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return ResponseEntity.ok(memberService.updateMotto(mottoRequestDto, userDetailsImpl));
    }
    @PostMapping("/api/reissue")  //재발급을 위한 로직
    public ResponseEntity<MemberResponseDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        MemberResponseDto memberResponseDto = memberService.reissue(tokenRequestDto);
        String accessToken = memberResponseDto.getAccessToken();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization",accessToken);

        return  new ResponseEntity<>(null, httpHeaders, HttpStatus.OK);
//        return ResponseEntity.ok(memberService.reissue(tokenRequestDto));
    }
    @GetMapping("api/d-day")
    public ResponseEntity<GoalDateResponseDto> getRemainingDay(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return ResponseEntity.ok()
                .body(memberService.getRemaingDay(userDetailsImpl));
    }
    @PutMapping("api/d-day")
    public ResponseEntity<String> updateGoal(@RequestBody GoalDateRequestDto goalDateRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return ResponseEntity.ok()
                .body(memberService.updateGoal(goalDateRequestDto, userDetailsImpl));
    }
}
