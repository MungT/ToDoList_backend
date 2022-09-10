package sparta.seed.todo.repository;


import sparta.seed.login.domain.Member;
import sparta.seed.todo.dto.AchievementResponseDto;
import sparta.seed.todo.dto.TodoResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface RankRepositoryCustom {
    List<TodoResponseDto> getDaylyAchievementRate(LocalDate yesterDay);
    List<AchievementResponseDto> getRankTable(LocalDate stardDate, LocalDate endDate);
}

