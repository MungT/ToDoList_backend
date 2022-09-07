package sparta.seed.todo.service;

import lombok.RequiredArgsConstructor;
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

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final TimeCustom timeCustom;

    public void isWriter(Todo todo, Member member) {

        if (!todo.getMember().getId().equals(member.getId()))
            throw new CustomException(ErrorCode.NOT_WRITER);
    }

    public void isPassedAvailableTime(Todo todo) {
        LocalDate currentDate = timeCustom.currentDate();
        if (!currentDate.equals(todo.getAddDate())) {
            throw new CustomException(ErrorCode.PASSED_AVAILABLE_TIME);
        }
    }

    public List<TodoResponseDto> getTodo(UserDetailsImpl userDetails) {
        LocalDate currentDate = timeCustom.currentDate();
        return todoRepository.findAllbyAddDateAndMember(currentDate, userDetails.getMember());
    }

    public TodoResponseDto addTodo(TodoRequestDto todoRequestDto, UserDetailsImpl userDetailsImpl) {

        Todo todo = Todo.builder()
                .content(todoRequestDto.getContent())
                .isComplete(todoRequestDto.getIsComplete())
                .addDate(timeCustom.currentDate())
                .member(userDetailsImpl.getMember())
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

    public AchievementResponseDto getAchievementRate(UserDetailsImpl userDetailsImpl) {
        LocalDate currentDate = timeCustom.currentDate();
        List<TodoResponseDto> todoResponseDtoList = todoRepository.getAchievementRateByDate(currentDate, userDetailsImpl.getMember());
        long totalCnt = todoResponseDtoList.get(0).getCount() + todoResponseDtoList.get(1).getCount();
        float percent = (float) todoResponseDtoList.get(1).getCount() / totalCnt;
        return AchievementResponseDto.builder()
                .totalCnt(totalCnt)
                .completeCnt(todoResponseDtoList.get(1).getCount())
                .achievementRate(Math.round(percent * 10000) / 100.0 + "%")
                .build();
    }

//    public TodoDateResponseDto getTodoDateResponseDto(UserDetailsImpl userDetailsImpl) {
//        return todoRepository.getFirstandLastTodoAddDate(userDetailsImpl.getMember());
//    }

    public List<AchievementResponseDto> getWeeklyAchievementRate(UserDetailsImpl userDetailsImpl) {
        TodoDateResponseDto todoDateResponseDto = todoRepository.getFirstandLastTodoAddDate(userDetailsImpl.getMember());
        LocalDate firstTodoAddDate = todoDateResponseDto.getFirstTodoAddDate();
        LocalDate lastTodoAddDate = todoDateResponseDto.getLastTodoAddDate();
        FirstWeekResponseDto firstWeekResponseDto = timeCustom.getDayOfWeek(firstTodoAddDate);
        LocalDate startDate = firstWeekResponseDto.getStartDate();
        LocalDate endDate = firstWeekResponseDto.getEndDate();

        List<AchievementResponseDto> achievementResponseDtoList = new ArrayList<>();

        do {
            List<TodoResponseDto> todoResponseDtoList = todoRepository.getWeeklyAchievementRate(startDate, endDate, userDetailsImpl.getMember());
            long totalCnt = todoResponseDtoList.get(0).getCount() + todoResponseDtoList.get(1).getCount();
            float percent = (float) todoResponseDtoList.get(1).getCount() / totalCnt;

            achievementResponseDtoList.add(AchievementResponseDto.builder()
                    .totalCnt(totalCnt)
                    .completeCnt(todoResponseDtoList.get(1).getCount())
                    .achievementRate(Math.round(percent * 10000) / 100.0 + "%")
                    .build());

            if(endDate.equals(lastTodoAddDate))
                break;

            startDate = endDate.plusDays(1);
            endDate = endDate.plusDays(7);

            if(endDate.isAfter(lastTodoAddDate)){
                endDate = lastTodoAddDate;
            }
        } while (lastTodoAddDate.isAfter(startDate));

        return achievementResponseDtoList;
    }
}