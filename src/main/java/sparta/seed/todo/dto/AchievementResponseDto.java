package sparta.seed.todo.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AchievementResponseDto {
    private String achievementRate;
    private long totalCnt;
    private long completeCnt;

    @Builder
    public AchievementResponseDto(String achievementRate, long totalCnt, long completeCnt) {
        this.achievementRate = achievementRate;
        this.totalCnt = totalCnt;
        this.completeCnt = completeCnt;
    }
}

