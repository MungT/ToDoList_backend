package sparta.seed.todo.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class TodoRequestDto {

    @NotBlank(message = "내용을 입력하세요")
    @Size(min=2, max=15, message = "투두 내용은 2~12자리여야 합니다.")
    private String content;

    private boolean isComplete;
}
