package sparta.seed.todo.repository;


import sparta.seed.login.domain.Member;
import sparta.seed.todo.dto.TodoResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepositoryCustom {
//    List<MemberTeamDto> search(MemberSearchCondition condition);
    List<TodoResponseDto> findAllbyAddDateAndMember(LocalDate addDate, Member member);
    List<TodoResponseDto> getAchievementRateByDate(LocalDate addDate, Member member);
//    List<TodoResponseDto> getWeeklyAchievementRate(String stardDate, String endDate, Member memeber);
}

