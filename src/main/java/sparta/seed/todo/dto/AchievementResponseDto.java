package sparta.seed.todo.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AchievementResponseDto {
    private String AchievementRate;
    private long totalCnt;
    private long completeCnt;

    @Builder
    public AchievementResponseDto(String achievementRate, long totalCnt, long completeCnt) {
        AchievementRate = achievementRate;
        this.totalCnt = totalCnt;
        this.completeCnt = completeCnt;
    }
}

