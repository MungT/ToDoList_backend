package sparta.seed.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.seed.exception.CustomException;
import sparta.seed.exception.ErrorCode;
import sparta.seed.login.domain.Member;
import sparta.seed.login.repository.MemberRepository;
import sparta.seed.message.Message;
import sparta.seed.sercurity.UserDetailsImpl;
import sparta.seed.todo.domain.Todo;
import sparta.seed.todo.dto.TodoRequestDto;
import sparta.seed.todo.dto.TodoResponseDto;
import sparta.seed.todo.repository.TodoRepository;
import sparta.seed.util.TimeCustom;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final TimeCustom timeCustom;
    private final MemberRepository memberRepository;


    // 5to5를 하루로 보는데, 프론트에서 만약 22일 새벽 1시에 api 호출할 때
    //달력 라이브러리로 인해 21일이 아닌 22일로 호출된다고해서 아래 메소드를 따로 생성함.
    public List<TodoResponseDto> getTodayTodo(UserDetailsImpl userDetails) {
        LocalDate localDate = timeCustom.currentDate();
        if(!memberRepository.existsByNickname(userDetails.getNickname()))
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        return todoRepository.getTodayTodo(localDate, userDetails.getMember());
    }
    //없을 시 빈 리스트 반환
    public List<TodoResponseDto> getTodo(String selectDate, UserDetailsImpl userDetails) {

        LocalDate selectedDate = LocalDate.parse(selectDate, DateTimeFormatter.ISO_DATE);
        if(!memberRepository.existsByNickname(userDetails.getNickname()))
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        return todoRepository.getTodo(selectedDate, userDetails.getMember());
    }

    public String addTodo(TodoRequestDto todoRequestDto, UserDetailsImpl userDetailsImpl) {

        Todo todo = Todo.builder()
                .content(todoRequestDto.getContent())
                .isComplete(todoRequestDto.getIsComplete())
                .addDate(timeCustom.currentDate())
                .nickname(userDetailsImpl.getMember().getNickname())
                .category(todoRequestDto.getCategory())
                .build();
        todoRepository.save(todo);

        return Message.TODO_UPLOAD_SUCCESS.getMessage();
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

}