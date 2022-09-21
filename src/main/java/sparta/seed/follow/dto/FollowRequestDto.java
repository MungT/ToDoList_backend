package sparta.seed.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sparta.seed.login.domain.Member;

@Getter
@AllArgsConstructor
public class FollowRequestDto {

    private Member FromMember;
    private Member toMember;
}
