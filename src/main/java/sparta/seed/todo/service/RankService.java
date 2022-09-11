package sparta.seed.todo.service;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.seed.exception.CustomException;
import sparta.seed.exception.ErrorCode;
import sparta.seed.login.domain.Member;
import sparta.seed.sercurity.UserDetailsImpl;
import sparta.seed.todo.domain.Rank;
import sparta.seed.todo.domain.Todo;
import sparta.seed.todo.dto.*;
import sparta.seed.todo.repository.RankRepository;
import sparta.seed.todo.repository.TodoRepository;
import sparta.seed.util.TimeCustom;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RankService {

    private final RankRepository rankRepository;
    private final TimeCustom timeCustom;

    public List<AchievementResponseDto> getRankTable(Long range) {
        //주간 랭킹 점수
        LocalDate endDate = timeCustom.currentDate();
        LocalDate startDate;
        if (range == 7) {
            startDate = endDate.minusDays(endDate.getDayOfWeek().getValue());
        } else {
            //월간 랭킹 점수
            startDate = endDate.minusDays(endDate.getDayOfMonth() - 1);
        }
        List<AchievementResponseDto> achievementResponseDtoList = rankRepository.getRankTable(startDate, endDate);
        achievementResponseDtoList.get(0).setRank(1);
        int a = 1;
        for (int i = 1; i < achievementResponseDtoList.size(); i++) {
            if (achievementResponseDtoList.get(i).getScore() != achievementResponseDtoList.get(i - 1).getScore()) {
                achievementResponseDtoList.get(i).setRank(++a);
            }
        }
        return achievementResponseDtoList;
    }
    //없을 시 빈 리스트 반환
    public void saveRankTable() {


        LocalDate yesterDay = timeCustom.currentDate().minusDays(1);

        List<AchievementResponseDto> achievementResponseDtoList = new ArrayList<>();
        List<Rank> rankList = new ArrayList<>();

        List<TodoResponseDto> todoResponseDtoList = rankRepository.saveRankTable(yesterDay);
        //데이터가 없거나 true or false로 하나만 있을 경우
        switch (todoResponseDtoList.size()) {
            case 0:
                throw new CustomException(ErrorCode.TODO_NOT_FOUND);
            case 1:
                if (todoResponseDtoList.get(0).isComplete()) {
                    achievementResponseDtoList.add(AchievementResponseDto.builder()
                            .totalCnt(todoResponseDtoList.get(0).getCount())
                            .completeCnt(todoResponseDtoList.get(0).getCount())
                            .score(100)
                            .nickname(todoResponseDtoList.get(0).getNickname())
                            .addDate(todoResponseDtoList.get(0).getAddDate())
                            .build());
                    Rank rank = Rank.builder()
                            .nickname(achievementResponseDtoList.get(0).getNickname())
                            .addDate(achievementResponseDtoList.get(0).getAddDate())
                            .score(achievementResponseDtoList.get(0).getScore()).build();

                    rankRepository.save(rank);
                } else {
                    achievementResponseDtoList.add(AchievementResponseDto.builder()
                            .totalCnt(todoResponseDtoList.get(0).getCount())
                            .completeCnt(0)
                            .score(0)
                            .nickname(todoResponseDtoList.get(0).getNickname())
                            .addDate(todoResponseDtoList.get(0).getAddDate())
                            .build());
                    Rank rank = Rank.builder()
                            .nickname(achievementResponseDtoList.get(0).getNickname())
                            .addDate(achievementResponseDtoList.get(0).getAddDate())
                            .score(achievementResponseDtoList.get(0).getScore()).build();

                    rankRepository.save(rank);
                }
        }
        long totalCnt = 0;
        float percent = 0;
        if (todoResponseDtoList.size() > 1) {
            for (int i = 0; i < todoResponseDtoList.size(); i++) {
                if (todoResponseDtoList.get(i).getAddDate().isEqual(todoResponseDtoList.get(i + 1).getAddDate())) {
                    totalCnt = todoResponseDtoList.get(i).getCount() + todoResponseDtoList.get(i + 1).getCount();
                    percent = (float) todoResponseDtoList.get(i + 1).getCount() / totalCnt;
                    achievementResponseDtoList.add(AchievementResponseDto.builder()
                            .totalCnt(totalCnt)
                            .completeCnt(todoResponseDtoList.get(i + 1).getCount())
                            .score(Math.round(percent * 10000) / 100.0)
                            .nickname(todoResponseDtoList.get(i).getNickname())
                            .addDate(todoResponseDtoList.get(i).getAddDate())
                            .build());
                    //그 날짜에 true, false가 모두 존재할 경우 row 2줄을 처리하고 다음으로 넘어가야하기 때문에 i++
                    i++;
                } else {
                    totalCnt = todoResponseDtoList.get(i).getCount();
                    if (todoResponseDtoList.get(i).isComplete()) {
                        achievementResponseDtoList.add(AchievementResponseDto.builder()
                                .totalCnt(totalCnt)
                                .completeCnt(totalCnt)
                                .achievementRate(100)
                                .addDate(todoResponseDtoList.get(i).getAddDate())
                                .nickname(todoResponseDtoList.get(i).getNickname())
                                .build());
                    } else {
                        achievementResponseDtoList.add(AchievementResponseDto.builder()
                                .totalCnt(totalCnt)
                                .completeCnt(0)
                                .achievementRate(0)
                                .addDate(todoResponseDtoList.get(i).getAddDate())
                                .nickname(todoResponseDtoList.get(i).getNickname())
                                .build());
                    }
                }
            }
        }
        for (AchievementResponseDto achievementResponseDto : achievementResponseDtoList) {
            rankList.add(Rank.builder()
                    .nickname(achievementResponseDto.getNickname())
                    .addDate(achievementResponseDto.getAddDate())
                    .score(achievementResponseDto.getScore())
                    .build());
            rankRepository.saveAll(rankList);
        }
    }


}