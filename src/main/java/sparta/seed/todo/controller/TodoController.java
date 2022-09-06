package sparta.seed.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.seed.message.Message;
import sparta.seed.sercurity.UserDetailsImpl;
import sparta.seed.todo.domain.Todo;
import sparta.seed.todo.dto.AchievementResponseDto;
import sparta.seed.todo.dto.TodoRequestDto;
import sparta.seed.todo.dto.TodoResponseDto;
import sparta.seed.todo.dto.TodoSearchCondition;
import sparta.seed.todo.service.TodoService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/api/todo")
    public ResponseEntity<List<TodoResponseDto>> getTodo(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {//@RequestParam을 선언 안해줘도 VO를 넣어주면 일치하는 VO의 멤버변수에 값이 들어간다.
        return ResponseEntity.ok()
                .body(todoService.getTodo(userDetailsImpl));
    }

    @PostMapping("/api/todo")
    public ResponseEntity<TodoResponseDto> addTodo(@Valid @RequestBody TodoRequestDto todoRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return ResponseEntity.ok()
                .body(todoService.addTodo(todoRequestDto, userDetailsImpl));
    }

    @PutMapping("/api/todo/{todoId}")
    public ResponseEntity<String> updateTodo(
            @PathVariable Long todoId,
            @Valid @RequestBody TodoRequestDto todoRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        todoService.updateTodo(todoId, todoRequestDto, userDetailsImpl);
        return ResponseEntity.ok()
                .body(Message.TODO_UPDATE_SUCCESS.getMessage());
    }

    @DeleteMapping("/api/todo/{todoId}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long todoId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        todoService.deleteTodo(todoId, userDetails);
        return ResponseEntity.ok()
                .body(Message.TODO_DELETE_SUCCESS.getMessage());
    }

    @GetMapping("/api/todo/achievement")
    public ResponseEntity<AchievementResponseDto> getAchievementRate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return ResponseEntity.ok()
                .body(todoService.getAchievementRate(userDetailsImpl));
    }
}
