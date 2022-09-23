package sparta.seed.follow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.seed.follow.dto.FindResponseDto;
import sparta.seed.follow.dto.FollowCountDto;
import sparta.seed.follow.service.FollowService;
import sparta.seed.sercurity.UserDetailsImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @GetMapping("/api/follow/{toMemberId}")
    public String follow(@PathVariable Long toMemberId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return followService.upDownFollow(toMemberId, userDetailsImpl);
    }

    //팔로잉 리스트 ( 구독한 유저 리스트 )
    @GetMapping("/api/followings")
    public List<FindResponseDto> findFollowing(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
    return followService.getFollowings(userDetailsImpl);
}
    // 팔로워 리스트 ( 나를 구독한 유저 리스트 )
    @GetMapping("/api/followers")
    public List<FindResponseDto> findFollower(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return followService.getFollowers(userDetailsImpl);
    }

}
