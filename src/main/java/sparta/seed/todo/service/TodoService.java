package sparta.seed.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.seed.exception.CustomException;
import sparta.seed.exception.ErrorCode;
import sparta.seed.exception.ErrorResponse;
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
        LocalDate selectedDate = timeCustom.currentDate();

        List<TodoResponseDto> todoResponseDtoList = todoRepository.getAchievementRateByDate(selectedDate, userDetailsImpl.getMember());
        switch (todoResponseDtoList.size()) {
            case 0:
                throw new CustomException(ErrorCode.TODO_NOT_FOUND);
            case 1:
                if (todoResponseDtoList.get(0).isComplete()) {
                    return AchievementResponseDto.builder()
                            .totalCnt(todoResponseDtoList.get(0).getCount())
                            .completeCnt(todoResponseDtoList.get(0).getCount())
                            .achievementRate(100 + "%")
                            .build();
                } else {
                    return AchievementResponseDto.builder()
                            .totalCnt(todoResponseDtoList.get(0).getCount())
                            .completeCnt(0)
                            .achievementRate(0 + "%")
                            .build();
                }
        }
        long totalCnt = todoResponseDtoList.get(0).getCount() + todoResponseDtoList.get(1).getCount();
        float percent = (float) todoResponseDtoList.get(1).getCount() / totalCnt;
        return AchievementResponseDto.builder()
                .totalCnt(totalCnt)
                .completeCnt(todoResponseDtoList.get(1).getCount())
                .achievementRate(Math.round(percent * 10000) / 100.0 + "%")
                .build();
    }

    public List<AchievementResponseDto> getDaylyAchievementRate(UserDetailsImpl userDetailsImpl) {


        LocalDate endDate = timeCustom.currentDate();
        LocalDate startDate = endDate.minusDays(30);

        List<AchievementResponseDto> achievementResponseDtoList = new ArrayList<>();

        List<TodoResponseDto> todoResponseDtoList = todoRepository.getDaylyAchievementRate(startDate, endDate, userDetailsImpl.getMember());
        switch (todoResponseDtoList.size()) {
            case 0:
                throw new CustomException(ErrorCode.TODO_NOT_FOUND);
            case 1:
                if (todoResponseDtoList.get(0).isComplete()) {
                    achievementResponseDtoList.add(AchievementResponseDto.builder()
                            .totalCnt(todoResponseDtoList.get(0).getCount())
                            .completeCnt(todoResponseDtoList.get(0).getCount())
                            .achievementRate(100 + "%")
                            .addDate(todoResponseDtoList.get(0).getAddDate())
                            .build());
                    return achievementResponseDtoList;

                } else {
                    achievementResponseDtoList.add(AchievementResponseDto.builder()
                            .totalCnt(todoResponseDtoList.get(0).getCount())
                            .completeCnt(0)
                            .achievementRate(0 + "%")
                            .addDate(todoResponseDtoList.get(0).getAddDate())
                            .build());
                    return achievementResponseDtoList;
                }
        }
        long totalCnt = 0;
        float percent = 0;
        for (int i = 0; i < todoResponseDtoList.size(); i++) {
            totalCnt = todoResponseDtoList.get(i).getCount() + todoResponseDtoList.get(i + 1).getCount();
            percent = (float) todoResponseDtoList.get(i + 1).getCount() / totalCnt;
            achievementResponseDtoList.add(AchievementResponseDto.builder()
                    .totalCnt(totalCnt)
                    .completeCnt(todoResponseDtoList.get(i + 1).getCount())
                    .achievementRate(Math.round(percent * 10000) / 100.0 + "%")
                    .addDate(todoResponseDtoList.get(i + 1).getAddDate())
                    .build());
        }

        return achievementResponseDtoList;
    }

    public List<AchievementResponseDto> getWeeklyAchievementRate(UserDetailsImpl userDetailsImpl) {
        Member member = userDetailsImpl.getMember();
        if (!todoRepository.existsByMember(member))
            throw new CustomException(ErrorCode.TODO_NOT_FOUND);
        TodoDateResponseDto todoDateResponseDto = todoRepository.getFirstandLastTodoAddDate(member);
        LocalDate firstTodoAddDate = todoDateResponseDto.getFirstTodoAddDate();
        LocalDate lastTodoAddDate = todoDateResponseDto.getLastTodoAddDate();
        FirstWeekResponseDto firstWeekResponseDto = timeCustom.getDayOfWeek(firstTodoAddDate);
        LocalDate startDate = firstWeekResponseDto.getStartDate();
        LocalDate endDate = firstWeekResponseDto.getEndDate();

        List<AchievementResponseDto> achievementResponseDtoList = new ArrayList<>();

        do {
            List<TodoResponseDto> todoResponseDtoList = todoRepository.getWeeklyAchievementRate(startDate, endDate, member);
            long totalCnt = 0;
            float percent = 0;
            if (todoResponseDtoList.size() != 0) {
                totalCnt = todoResponseDtoList.get(0).getCount() + todoResponseDtoList.get(1).getCount();
                percent = (float) todoResponseDtoList.get(1).getCount() / totalCnt;

                achievementResponseDtoList.add(AchievementResponseDto.builder()
                        .totalCnt(totalCnt)
                        .completeCnt(todoResponseDtoList.get(1).getCount())
                        .achievementRate(Math.round(percent * 10000) / 100.0 + "%")
                        .build());
            } else {
                achievementResponseDtoList.add(AchievementResponseDto.builder()
                        .totalCnt(totalCnt)
                        .completeCnt(0)
                        .achievementRate(Math.round(percent * 10000) / 100.0 + "%")
                        .build());
            }


            if (endDate.equals(lastTodoAddDate))
                break;

            startDate = endDate.plusDays(1);
            endDate = endDate.plusDays(7);

           /* endDate가 마지막 추가날짜보다 크더라도 에러는 안난다.(조건에 안맞는것은 그냥 포함을 안시키기 때문)

           if (endDate.isAfter(lastTodoAddDate)) {
               endDate = lastTodoAddDate;
           }

            */

        } while (lastTodoAddDate.isAfter(startDate));


        return achievementResponseDtoList;
    }

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
}