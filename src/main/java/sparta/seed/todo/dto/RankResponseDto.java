package sparta.seed.todo.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;


@Getter
public class RankResponseDto {
    Long rankCnt;
    int ranking;
    double score;
    String nickname;
    String category;
    int lengthOfMonth;


    @QueryProjection
    public RankResponseDto(Long rankCnt) {
        this.rankCnt = rankCnt;
    }

    @Builder
    public RankResponseDto(Long rankCnt, int ranking, double score, String nickname, String category, int lengthOfMonth) {
        this.rankCnt = rankCnt;
        this.ranking = ranking;
        this.score = score;
        this.nickname = nickname;
        this.category = category;
        this.lengthOfMonth = lengthOfMonth;
    }
}
