package sparta.seed.follow.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FollowCountDto {

    private Long userId;
    private String Nickname;
    private int followingsCnt;
    private int followersCnt;

    @Builder
    public FollowCountDto(Long userId, String nickname, int followingsCnt, int followersCnt) {
        this.userId = userId;
        this.Nickname = nickname;
        this.followingsCnt = followingsCnt;
        this.followersCnt = followersCnt;
    }
}
