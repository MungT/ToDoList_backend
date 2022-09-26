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
    private String nickname;
    private String category;

    @Builder
    @QueryProjection
    public TodoResponseDto(long todoId, String content, boolean isComplete, LocalDate addDate, String category) {
        this.todoId = todoId;
        this.content = content;
        this.isComplete = isComplete;
        this.addDate = addDate;
        this.category = category;
    }

    @QueryProjection
    public TodoResponseDto(String nickname, boolean isComplete, LocalDate addDate, long count){
        this.nickname = nickname;
        this.isComplete = isComplete;
        this.addDate = addDate;
        this.count = count;
    }
    @QueryProjection
    public TodoResponseDto(boolean isComplete, LocalDate addDate, long count){
        this.isComplete = isComplete;
        this.addDate = addDate;
        this.count = count;
    }

    @QueryProjection
    public TodoResponseDto(boolean isComplete, long count){
        this.isComplete = isComplete;
        this.count = count;
    }
    @QueryProjection
    public TodoResponseDto(long count){
        this.count = count;
    }

}

