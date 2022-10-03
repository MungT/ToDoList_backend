package sparta.seed.follow.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FollowResponseDto {

    private final String nickname;
    private final int followingCnt;
    private final int followerCnt;

    @QueryProjection
    @Builder
    public FollowResponseDto(String nickname, int followingCnt, int followerCnt) {
        this.nickname = nickname;
        this.followingCnt = followingCnt;
        this.followerCnt = followerCnt;
    }

}
