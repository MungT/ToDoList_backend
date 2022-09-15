package sparta.seed.todo.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class FirstWeekResponseDto {
    LocalDate startDate;
    LocalDate endDate;

    public FirstWeekResponseDto(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
