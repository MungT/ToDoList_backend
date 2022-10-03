package sparta.seed.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.seed.exception.CustomException;
import sparta.seed.exception.ErrorCode;
import sparta.seed.todo.domain.Rank;
import sparta.seed.todo.dto.AchievementResponseDto;
import sparta.seed.todo.dto.RankResponseDto;
import sparta.seed.todo.repository.RankRepository;
import sparta.seed.util.TimeCustom;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RankService {
    private final RankRepository rankRepository;
    private final TimeCustom timeCustom;

    public List<Rank> saveWeeklyRank() {
        //주간 랭킹 점수
        LocalDate currentDate = timeCustom.currentDate();
        //월요일은 아직 랭킹 테이블에 안올라온 상태이기 때문에 문제없음
        if (currentDate.getDayOfWeek().equals(DayOfWeek.TUESDAY)) { //원래는 Tuesday지만 테스트를 위해서 wednesday
            rankRepository.deleteRank("지난 주");
            rankRepository.setThisWeekToLastWeek();
        }

        LocalDate endDate = currentDate.minusDays(1);
        LocalDate startDate = endDate.minusDays(endDate.getDayOfWeek().getValue() - 1);

        List<Rank> rankList = new ArrayList<>();
        rankRepository.deleteRank("주간");
        List<AchievementResponseDto> achievementResponseDtoList = saveRank(startDate, endDate);

        for (AchievementResponseDto achievementResponseDto : achievementResponseDtoList) {
            rankList.add(Rank.builder()
                    .nickname(achievementResponseDto.getNickname())
                    .ranking(achievementResponseDto.getRank())
                    .score(achievementResponseDto.getAchievementRate())
                    .category("주간")
                    .build());
        }
        return rankRepository.saveAll(rankList);
    }

    //없을 시 빈 리스트 반환
    public List<Rank> saveMonthlyRank() {
        LocalDate endDate = timeCustom.currentDate().minusDays(1);
        LocalDate startDate = endDate.minusDays(endDate.getDayOfMonth() - 1);

        List<Rank> rankList = new ArrayList<>();
        rankRepository.deleteRank("월간");
        List<AchievementResponseDto> achievementResponseDtoList = saveRank(startDate, endDate);

        for (AchievementResponseDto achievementResponseDto : achievementResponseDtoList) {
            rankList.add(Rank.builder()
                    .id(achievementResponseDto.getId())
                    .nickname(achievementResponseDto.getNickname())
                    .ranking(achievementResponseDto.getRank())
                    .score(achievementResponseDto.getAchievementRate())
                    .category("월간")
                    .build());
        }

        return rankRepository.saveAll(rankList);
    }

    public List<AchievementResponseDto> saveRank(LocalDate startDate, LocalDate endDate) {

        List<AchievementResponseDto> achievementResponseDtoList = rankRepository.getUserByOrderByScoreDesc(startDate, endDate);
        achievementResponseDtoList.get(0).setRank(1);
        int rank = 1;
        int achievementResponseDtoListSize = achievementResponseDtoList.size();
        for (int i = 1; i < achievementResponseDtoListSize; i++) {
            if (achievementResponseDtoList.get(i).getAchievementRate() != achievementResponseDtoList.get(i - 1).getAchievementRate()) {
                achievementResponseDtoList.get(i).setRank(++rank);
            } else {
                achievementResponseDtoList.get(i).setRank(rank);
            }
        }
        return achievementResponseDtoList;

    }

    public Rank getLastweekRank(String nickname) {

        Rank rank = rankRepository.getLastweekRank(nickname);
        if (rank == null) {
            return Rank.builder()
                    .ranking(0)
                    .score(0)
                    .nickname(nickname)
                    .build();
        }
        return rank;
    }

    public Rank getWeeklyRank(String nickname) {
        Rank rank = rankRepository.getWeeklyRank(nickname);
        if (rank == null) {
            return Rank.builder()
                    .ranking(0)
                    .score(0)
                    .nickname(nickname)
                    .build();
        }
        return rank;
    }

    public RankResponseDto getMonthlyRank(String nickname) {
        Rank rank = rankRepository.getMonthlyRank(nickname);
        if (rank == null)
            return RankResponseDto.builder()
                    .ranking(0)
                    .score(0)
                    .nickname(nickname)
                    .lengthOfMonth(timeCustom.currentDate().lengthOfMonth())
                    .build();
        else {
            return RankResponseDto.builder()
                    .ranking(rank.getRanking())
                    .score(rank.getScore())
                    .nickname(rank.getNickname())
                    .lengthOfMonth(timeCustom.currentDate().lengthOfMonth())
                    .build();
        }

    }
}