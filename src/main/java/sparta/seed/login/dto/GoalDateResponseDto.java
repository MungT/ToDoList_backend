package sparta.seed.login.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GoalDateResponseDto {
    private String title;
    private int remaingDay;

    @Builder
    public GoalDateResponseDto(String title, int remaingDay) {
        this.title = title;
        this.remaingDay = remaingDay;
    }
}
