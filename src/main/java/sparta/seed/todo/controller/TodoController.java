package sparta.seed.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.seed.todo.domain.Todo;
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
  public ResponseEntity<List<TodoResponseDto>> getTodo(){//@RequestParam을 선언 안해줘도 VO를 넣어주면 일치하는 VO의 멤버변수에 값이 들어간다.
    return ResponseEntity.ok()
            .body(todoService.getTodo());
  }
  @PostMapping("/api/todo")
  public ResponseEntity<TodoResponseDto> addTodo(@Valid @RequestBody TodoRequestDto todoRequestDto){
    return ResponseEntity.ok()
            .body(todoService.addTodo(todoRequestDto));
  }

}
