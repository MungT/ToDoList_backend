package sparta.seed.todo.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;


@Getter
public class RankResponseDto {
    Long rankCnt;


    @QueryProjection
    public RankResponseDto(Long rankCnt) {
        this.rankCnt = rankCnt;
    }

}
