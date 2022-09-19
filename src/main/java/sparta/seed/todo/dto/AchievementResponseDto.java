package sparta.seed.todo.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AchievementResponseDto {
    private long id;
    private double achievementRate;
    private long totalCnt;
    private long completeCnt;
    private String nickname;
    private LocalDate addDate;
    private int rank;
    private long plannerCnt;

    @Builder
    public AchievementResponseDto(long id, double achievementRate, long totalCnt, long completeCnt, String nickname, LocalDate addDate, long plannerCnt, int rank) {
        this.id = id;
        this.achievementRate = achievementRate;
        this.totalCnt = totalCnt;
        this.completeCnt = completeCnt;
        this.nickname = nickname;
        this.addDate = addDate;
        this.plannerCnt = plannerCnt;
        this.rank = rank;
    }

    @QueryProjection
    public AchievementResponseDto(String nickname, double achievementRate) {
        this.nickname = nickname;
        this.achievementRate = achievementRate;
    }
    @QueryProjection
    public AchievementResponseDto(double achievementRate, long plannerCnt) {
        this.achievementRate = achievementRate;
        this.plannerCnt = plannerCnt;
    }
    @QueryProjection
    public AchievementResponseDto(long id, LocalDate addDate, double achievementRate) {
        this.id = id;
        this.addDate = addDate;
        this.achievementRate = achievementRate;

    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setAchievementRate(double achievementRate) {
        this.achievementRate = achievementRate;
    }
}

