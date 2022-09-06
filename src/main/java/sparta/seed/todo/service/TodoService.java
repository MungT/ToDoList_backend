package sparta.seed.todo.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.seed.todo.domain.Todo;
import sparta.seed.todo.dto.TodoRequestDto;
import sparta.seed.todo.dto.TodoResponseDto;
import sparta.seed.todo.repository.TodoRepository;
import sparta.seed.util.TimeCustom;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final TimeCustom timeCustom;



    public List<TodoResponseDto> getTodo() {
        String addDate = timeCustom.addDate();
        return todoRepository.findAllbyAddDate(addDate);
    }

    public TodoResponseDto addTodo(TodoRequestDto todoRequestDto) {
        timeCustom.customTime();
        Todo todo = Todo.builder()
                .content(todoRequestDto.getContent())
                .isComplete(todoRequestDto.isComplete())
                .addDate(timeCustom.addDate())
                .build();
        todoRepository.save(todo);

        return TodoResponseDto.builder()
                .todoId(todo.getId())
                .content(todo.getContent())
                .isComplete(todo.getIsComplete())
                .addDate(todo.getAddDate())
                .build();
    }
}