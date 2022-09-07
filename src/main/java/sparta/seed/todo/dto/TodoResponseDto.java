package sparta.seed.todo.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TodoResponseDto {
    private long todoId;
    private String content;
    private boolean isComplete;
    private LocalDate addDate;
    private long count;

    @Builder
    @QueryProjection
    public TodoResponseDto(long todoId, String content, boolean isComplete, LocalDate addDate) {
        this.todoId = todoId;
        this.content = content;
        this.isComplete = isComplete;
        this.addDate = addDate;
    }

    @QueryProjection
    public TodoResponseDto(boolean isComplete, LocalDate addDate, long count){
        this.isComplete = isComplete;
        this.addDate = addDate;
        this.count = count;
    }
}

