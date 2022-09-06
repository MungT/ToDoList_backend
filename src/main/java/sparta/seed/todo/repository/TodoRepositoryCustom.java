package sparta.seed.todo.repository;


import sparta.seed.login.domain.Member;
import sparta.seed.todo.dto.TodoResponseDto;

import java.util.List;

public interface TodoRepositoryCustom {
//    List<MemberTeamDto> search(MemberSearchCondition condition);
    List<TodoResponseDto> findAllbyAddDateAndMember(String addDate, Member member);
    List<TodoResponseDto> getAchievementRateByDate(String addDate, Member member);
}

