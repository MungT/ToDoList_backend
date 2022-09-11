package sparta.seed.todo.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AchievementResponseDto {
    private double achievementRate;
    private double score;
    private long totalCnt;
    private long completeCnt;
    private String nickname;
    private LocalDate addDate;

    private int rank;

    @Builder
    public AchievementResponseDto(double achievementRate,double score, long totalCnt, long completeCnt, String nickname, LocalDate addDate) {
        this.achievementRate = achievementRate;
        this.score = score;
        this.totalCnt = totalCnt;
        this.completeCnt = completeCnt;
        this.nickname = nickname;
        this.addDate = addDate;
    }

    @QueryProjection
    public AchievementResponseDto(String nickname, double score) {
        this.score = score;
        this.nickname = nickname;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}

