package sparta.seed.todo.repository;

import sparta.seed.login.domain.Member;
import sparta.seed.todo.dto.AchievementResponseDto;
import sparta.seed.todo.dto.TodoResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface AchievementRepositoryCustom {
    List<TodoResponseDto> getRecentlyRate(LocalDate yesterDay);
    List<TodoResponseDto> getAchievementRateByDate(LocalDate selectedDate, Member member);
    List<TodoResponseDto> getDaylyAchievementRate(LocalDate stardDate, LocalDate endDate, Member memeber);
    List<TodoResponseDto> getWeeklyAchievementRate(LocalDate stardDate, LocalDate endDate, Member memeber);
    AchievementResponseDto getTotalAchievementRate(Member member);
    Long getPlannerCnt(Member member);
    List<AchievementResponseDto> getThisWeekAchievementRate(LocalDate startDate, LocalDate endDate, Member member);
    AchievementResponseDto getThisMonthAchievementRate(LocalDate startDate, LocalDate endDate, Member member);
}
