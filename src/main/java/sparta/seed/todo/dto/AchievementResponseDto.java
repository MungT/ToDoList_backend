package sparta.seed.todo.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AchievementResponseDto {
    private String achievementRate;
    private long totalCnt;
    private long completeCnt;

    private LocalDate addDate;

    @Builder
    public AchievementResponseDto(String achievementRate, long totalCnt, long completeCnt, LocalDate addDate) {
        this.achievementRate = achievementRate;
        this.totalCnt = totalCnt;
        this.completeCnt = completeCnt;
        this.addDate = addDate;
    }
}

