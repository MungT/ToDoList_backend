package sparta.seed.todo.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
public class TodoResponseDto {
    private long todoId;
    private String content;
    private boolean isComplete;

    @QueryProjection
    @Builder
    public TodoResponseDto(long todoId, String content, boolean isComplete) {
        this.todoId = todoId;
        this.content = content;
        this.isComplete = isComplete;
    }
}

