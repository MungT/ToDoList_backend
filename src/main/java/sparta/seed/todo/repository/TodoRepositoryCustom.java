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
    List<TodoResponseDto> getTodo(LocalDate addDate, Member member);
    TodoDateResponseDto getFirstandLastTodoAddDate(Member member);
    List<TodoResponseDto> getTodayTodo(LocalDate localDate, Member member);
    TodoResponseDto getTotalCnt(String nickname);
}

