package sparta.seed.todo.repository;

import sparta.seed.todo.dto.TodoResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface AchievementRepositoryCustom {
    List<TodoResponseDto> getRecentlyRate(LocalDate yesterDay);
}
