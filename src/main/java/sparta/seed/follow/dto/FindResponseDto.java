package sparta.seed.follow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class FindResponseDto {

    private Long toMemberId;
    private String nickname;

}
