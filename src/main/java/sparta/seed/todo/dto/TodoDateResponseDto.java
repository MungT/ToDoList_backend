package sparta.seed.todo.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDate;

@Getter
public class TodoDateResponseDto {

    LocalDate firstTodoAddDate;
    LocalDate lastTodoAddDate;

    @Builder
    @QueryProjection
    public TodoDateResponseDto(LocalDate firstTodoAddDate, LocalDate lastTodoAddDate) {
        this.firstTodoAddDate = firstTodoAddDate;
        this.lastTodoAddDate = lastTodoAddDate;
    }
}
