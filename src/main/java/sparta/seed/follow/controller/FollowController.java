package sparta.seed.follow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.seed.follow.dto.ResponseDto;
import sparta.seed.follow.service.FollowService;
import sparta.seed.login.domain.Member;
import sparta.seed.sercurity.UserDetailsImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping ("/api/follow/{toMemberId}")
    public String follow(@PathVariable Long toMemberId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return followService.upDownFollow(toMemberId, userDetailsImpl);
    }


    @GetMapping("/api/follow")
    public List<Member> findFollowing(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
    return followService.getFollows(userDetailsImpl);
}
}
