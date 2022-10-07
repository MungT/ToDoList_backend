package sparta.seed.follow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sparta.seed.exception.CustomException;
import sparta.seed.exception.ErrorCode;
import sparta.seed.follow.dto.FollowResponseDto;
import sparta.seed.follow.repository.FollowRepository;
import sparta.seed.follow.service.FollowService;
import sparta.seed.login.domain.Member;
import sparta.seed.login.repository.MemberRepository;
import sparta.seed.sercurity.UserDetailsImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;
    @Transactional
    @PostMapping("/api/follow/{toMemberId}")
    public String follow(@PathVariable Long toMemberId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return followService.upDownFollow(toMemberId, userDetailsImpl);
    }
    @GetMapping("/api/follow/{memberId}")
    public FollowResponseDto getFollowCnt(@PathVariable Long memberId){
        return followService.getFollowCnt(memberId);
    }

    //팔로잉 리스트 ( 구독한 유저 리스트 )
    @GetMapping("/api/followings/{nickname}")
    public List<Member> getFollowingList(@PathVariable String nickname) {
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        return followRepository.getFollowingList(member);
    }

    // 팔로워 리스트 ( 나를 구독한 유저 리스트 )
    @GetMapping("/api/followers/{nickname}")
    public List<Member> getFollowerList(@PathVariable String nickname) {
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        return followRepository.getFollowerList(member);
    }


}
