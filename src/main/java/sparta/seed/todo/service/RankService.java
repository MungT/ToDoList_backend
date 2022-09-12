package sparta.seed.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.seed.todo.domain.Rank;
import sparta.seed.todo.dto.AchievementResponseDto;
import sparta.seed.todo.repository.RankRepository;
import sparta.seed.util.TimeCustom;

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
        LocalDate endDate = timeCustom.currentDate().minusDays(1);
        LocalDate startDate = endDate.minusDays(endDate.getDayOfWeek().getValue()-1);

        List<Rank> rankList = new ArrayList<>();
        List<AchievementResponseDto> achievementResponseDtoList =  saveRank(startDate, endDate);

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
        List<AchievementResponseDto> achievementResponseDtoList =  saveRank(startDate, endDate);

        for (AchievementResponseDto achievementResponseDto : achievementResponseDtoList) {
            rankList.add(Rank.builder()
                    .nickname(achievementResponseDto.getNickname())
                    .ranking(achievementResponseDto.getRank())
                    .score(achievementResponseDto.getAchievementRate())
                    .category("월간")
                    .build());
        }

        return rankRepository.saveAll(rankList);
    }

    public List<AchievementResponseDto> saveRank(LocalDate startDate, LocalDate endDate){

        List<AchievementResponseDto> achievementResponseDtoList = rankRepository.saveRank(startDate, endDate);
        achievementResponseDtoList.get(0).setRank(1);
        int rank = 1;
        int achievementResponseDtoListSize = achievementResponseDtoList.size();
        for (int i = 1; i < achievementResponseDtoListSize; i++) {
            if (achievementResponseDtoList.get(i).getAchievementRate() != achievementResponseDtoList.get(i - 1).getAchievementRate()) {
                achievementResponseDtoList.get(i).setRank(++rank);
            } else{
                achievementResponseDtoList.get(i).setRank(rank);
            }
        }
        return achievementResponseDtoList;

    }
}