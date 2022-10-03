package sparta.seed.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sparta.seed.message.Message;
import sparta.seed.sercurity.UserDetailsImpl;
import sparta.seed.todo.domain.Todo;
import sparta.seed.todo.dto.*;
import sparta.seed.todo.repository.AchievementRepository;
import sparta.seed.todo.repository.TodoRepository;
import sparta.seed.todo.service.TodoService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class TodoController {

    private final TodoService todoService;
    private final TodoRepository todoRepository;
    //오늘 자 투두 조회
    @GetMapping("/api/todo/today/{nickname}")
    public ResponseEntity<List<TodoResponseDto>> getTodayTodo(@PathVariable String nickname){
        return ResponseEntity.ok()
                .body(todoService.getTodayTodo(nickname));
    }
    //선택한 날짜 투두 조회
    @GetMapping("/api/todo/{nickname}")
    public ResponseEntity<List<TodoResponseDto>> getTodo(@PathVariable String nickname, @RequestParam("date")String selectDate) {//@RequestParam을 선언 안해줘도 VO를 넣어주면 일치하는 VO의 멤버변수에 값이 들어간다.
        return ResponseEntity.ok()
                .body(todoService.getTodo(nickname, selectDate));
    }
    //총 투두 갯수 조회
    @GetMapping("/api/todo/total")
    public ResponseEntity<TodoResponseDto> getTotalCnt(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return ResponseEntity.ok()
                .body(todoService.getTotalCnt(userDetailsImpl));
    }
    //투두 추가
    @PostMapping("/api/todo")
    public ResponseEntity<String> addTodo(@Valid @RequestBody TodoRequestDto todoRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return ResponseEntity.ok()
                .body(todoService.addTodo(todoRequestDto, userDetailsImpl));
    }
    //투두 완료 및 수정
    @PutMapping("/api/todo/{todoId}")
    public ResponseEntity<String> updateTodo(
            @PathVariable Long todoId,
            @Valid @RequestBody TodoRequestDto todoRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        todoService.updateTodo(todoId, todoRequestDto, userDetailsImpl);
        return ResponseEntity.ok()
                .body(Message.TODO_UPDATE_SUCCESS.getMessage());
    }
    //투두 삭제
    @DeleteMapping("/api/todo/{todoId}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long todoId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        todoService.deleteTodo(todoId, userDetails);
        return ResponseEntity.ok()
                .body(Message.TODO_DELETE_SUCCESS.getMessage());
    }


}
