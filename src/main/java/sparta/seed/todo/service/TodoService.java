package sparta.seed.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.seed.exception.CustomException;
import sparta.seed.exception.ErrorCode;
import sparta.seed.login.domain.Member;
import sparta.seed.sercurity.UserDetailsImpl;
import sparta.seed.todo.domain.Todo;
import sparta.seed.todo.dto.*;
import sparta.seed.todo.repository.TodoRepository;
import sparta.seed.util.TimeCustom;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final TimeCustom timeCustom;


    //없을 시 빈 리스트 반환
    public List<TodoResponseDto> getTodo(UserDetailsImpl userDetails) {
        LocalDate currentDate = timeCustom.currentDate();

        return todoRepository.findAllbyAddDateAndMember(currentDate, userDetails.getMember());
    }

    public TodoResponseDto addTodo(TodoRequestDto todoRequestDto, UserDetailsImpl userDetailsImpl) {

        Todo todo = Todo.builder()
                .content(todoRequestDto.getContent())
                .isComplete(todoRequestDto.getIsComplete())
                .addDate(timeCustom.currentDate())
                .nickname(userDetailsImpl.getMember().getNickname())
                .build();
        todoRepository.save(todo);

        return TodoResponseDto.builder()
                .todoId(todo.getId())
                .content(todo.getContent())
                .isComplete(todo.getIsComplete())
                .addDate(todo.getAddDate())
                .build();
    }

    @Transactional
    public void updateTodo(Long todoId, TodoRequestDto todoRequestDto, UserDetailsImpl userDetailsImpl) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new CustomException(ErrorCode.TODO_NOT_FOUND)
        );
        Member member = userDetailsImpl.getMember();
        isWriter(todo, member);
        isPassedAvailableTime(todo);


        todo.update(todoRequestDto);
    }

    public void deleteTodo(Long todoId, UserDetailsImpl userDetailsImpl) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new CustomException(ErrorCode.TODO_NOT_FOUND)
        );
        Member member = userDetailsImpl.getMember();
        isWriter(todo, member);
        isPassedAvailableTime(todo);

        todoRepository.deleteById(todoId);
    }



    public void isWriter(Todo todo, Member member) {

        if (!todo.getNickname().equals(member.getNickname()))
            throw new CustomException(ErrorCode.NOT_WRITER);
    }

    public void isPassedAvailableTime(Todo todo) {
        LocalDate currentDate = timeCustom.currentDate();
        if (!currentDate.equals(todo.getAddDate())) {
            throw new CustomException(ErrorCode.PASSED_AVAILABLE_TIME);
        }
    }

    public void test() {
        SecurityContextHolder.getContext().getAuthentication();


    }
}