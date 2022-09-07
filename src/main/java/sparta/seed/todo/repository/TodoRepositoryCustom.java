package sparta.seed.todo.repository;


import com.querydsl.core.Tuple;
import sparta.seed.login.domain.Member;
import sparta.seed.todo.domain.Todo;
import sparta.seed.todo.dto.TodoDateResponseDto;
import sparta.seed.todo.dto.TodoResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepositoryCustom {
//    List<MemberTeamDto> search(MemberSearchCondition condition);
    List<TodoResponseDto> findAllbyAddDateAndMember(LocalDate addDate, Member member);
    List<TodoResponseDto> getAchievementRateByDate(LocalDate addDate, Member member);
    List<TodoResponseDto> getWeeklyAchievementRate(LocalDate stardDate, LocalDate endDate, Member memeber);
    TodoDateResponseDto getFirstandLastTodoAddDate(Member member);
}

