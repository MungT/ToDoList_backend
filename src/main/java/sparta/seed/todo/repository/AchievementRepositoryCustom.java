package sparta.seed.todo.repository;

import sparta.seed.login.domain.Member;
import sparta.seed.todo.dto.AchievementResponseDto;
import sparta.seed.todo.dto.TodoResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface AchievementRepositoryCustom {
    List<TodoResponseDto> getRecentlyRate(LocalDate yesterDay);
    List<TodoResponseDto> getAchievementRateByDate(LocalDate selectedDate, String nickname);
    List<AchievementResponseDto> getDaylyAchievementRate(LocalDate stardDate, LocalDate endDate, String nickname);
    List<TodoResponseDto> getWeeklyAchievementRate(LocalDate stardDate, LocalDate endDate, Member member);
    AchievementResponseDto getTotalAchievementRate(String nickname);
    Long getPlannerCnt(Member member);
    List<AchievementResponseDto> getThisWeekAchievementRate(LocalDate startDate, LocalDate endDate, String nickname);
    AchievementResponseDto getThisMonthAchievementRate(LocalDate startDate, LocalDate endDate, String nickname);
}
