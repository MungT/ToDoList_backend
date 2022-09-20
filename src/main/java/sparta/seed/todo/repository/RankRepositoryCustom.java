package sparta.seed.todo.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import sparta.seed.login.domain.Member;
import sparta.seed.sercurity.UserDetailsImpl;
import sparta.seed.todo.domain.Rank;
import sparta.seed.todo.dto.AchievementResponseDto;
import sparta.seed.todo.dto.RankResponseDto;
import sparta.seed.todo.dto.TodoResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface RankRepositoryCustom {
    List<AchievementResponseDto> saveRank(LocalDate stardDate, LocalDate endDate);
    Slice<AchievementResponseDto> getWeeklyPage(Pageable pageable);
    Slice<AchievementResponseDto> getMonthlyPage(Pageable pageable);
    Rank getLastweekRank(String nickname);
    Rank getWeeklyRank(String nickname);
    Rank getMonthlyRank(String nickname);
    RankResponseDto getWeeklyRankCnt();
    RankResponseDto getMonthlyRankCnt();
    void deleteLastWeek(String lastWeek);
    void setThisWeekToLastWeek();



}

