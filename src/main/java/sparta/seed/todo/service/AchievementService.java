package sparta.seed.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.seed.exception.CustomException;
import sparta.seed.exception.ErrorCode;
import sparta.seed.login.domain.Member;
import sparta.seed.sercurity.UserDetailsImpl;
import sparta.seed.todo.domain.Achievement;
import sparta.seed.todo.domain.Rank;
import sparta.seed.todo.dto.AchievementResponseDto;
import sparta.seed.todo.dto.FirstWeekResponseDto;
import sparta.seed.todo.dto.TodoDateResponseDto;
import sparta.seed.todo.dto.TodoResponseDto;
import sparta.seed.todo.repository.AchievementRepository;
import sparta.seed.todo.repository.TodoRepository;
import sparta.seed.util.TimeCustom;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AchievementService {
    private final TimeCustom timeCustom;
    private final TodoRepository todoRepository;
    private final AchievementRepository achievementRepository;

    public AchievementResponseDto getAchievementRate(String selectDate, UserDetailsImpl userDetailsImpl) {
        LocalDate selectedDate = LocalDate.parse(selectDate, DateTimeFormatter.ISO_DATE);

        List<TodoResponseDto> todoResponseDtoList = achievementRepository.getAchievementRateByDate(selectedDate, userDetailsImpl.getMember());
        switch (todoResponseDtoList.size()) {
            case 0:
                throw new CustomException(ErrorCode.TODO_NOT_FOUND);
            case 1:
                if (todoResponseDtoList.get(0).isComplete()) {
                    return AchievementResponseDto.builder()
                            .totalCnt(todoResponseDtoList.get(0).getCount())
                            .completeCnt(todoResponseDtoList.get(0).getCount())
                            .achievementRate(100)
                            .build();
                } else {
                    return AchievementResponseDto.builder()
                            .totalCnt(todoResponseDtoList.get(0).getCount())
                            .completeCnt(0)
                            .achievementRate(0)
                            .build();
                }
        }
        long totalCnt = todoResponseDtoList.get(0).getCount() + todoResponseDtoList.get(1).getCount();
        float percent = (float) todoResponseDtoList.get(1).getCount() / totalCnt;
        return AchievementResponseDto.builder()
                .totalCnt(totalCnt)
                .completeCnt(todoResponseDtoList.get(1).getCount())
                .achievementRate(Math.round(percent * 10000) / 100.0)
                .build();
    }
    //월화수목금토일 달성률 반환
    public List<AchievementResponseDto> getThisWeekAchievementRate(UserDetailsImpl userDetailsImpl) {
        LocalDate endDate = timeCustom.currentDate().minusDays(1);
        LocalDate startDate = endDate.minusDays(endDate.getDayOfWeek().getValue() - 1);
        Member member = userDetailsImpl.getMember();
        List<AchievementResponseDto> achievementResponseDtoList = achievementRepository.getThisWeekAchievementRate(startDate, endDate, member);
        int achievementResponseDtoListSize = achievementResponseDtoList.size();
        for(int i=1; i<achievementResponseDtoListSize; i++){
            achievementResponseDtoList.get(i).setAchievementRate(achievementResponseDtoList.get(i).getAchievementRate()+achievementResponseDtoList.get(i-1).getAchievementRate());
        }
        return achievementResponseDtoList;
    }
    //이번 달 달성률
    public AchievementResponseDto getThisMonthAchievementRate(UserDetailsImpl userDetailsImpl){
        LocalDate endDate = timeCustom.currentDate().minusDays(1);
        LocalDate startDate = endDate.minusDays(endDate.getDayOfMonth() - 1);
        Member member = userDetailsImpl.getMember();
        if (!achievementRepository.existsByNickname(member.getNickname()))
            throw new CustomException(ErrorCode.TODO_NOT_FOUND);
        AchievementResponseDto achievementResponseDto = achievementRepository.getThisMonthAchievementRate(startDate, endDate, member);

        return AchievementResponseDto.builder()
                .achievementRate(achievementResponseDto.getAchievementRate() / achievementResponseDto.getPlannerCnt())
                .build();
    }
    //최근 30일 각 날짜에 해당하는 투두리스트 달성률 리스트 반환
    public List<AchievementResponseDto> getDaylyAchievementRate(UserDetailsImpl userDetailsImpl) {

        LocalDate endDate = timeCustom.currentDate();
        LocalDate startDate = endDate.minusDays(30);

        List<AchievementResponseDto> achievementResponseDtoList = new ArrayList<>();

        List<TodoResponseDto> todoResponseDtoList = achievementRepository.getDaylyAchievementRate(startDate, endDate, userDetailsImpl.getMember());
        //데이터가 없거나 true or false로 하나만 있을 경우
        switch (todoResponseDtoList.size()) {
            case 0:
                throw new CustomException(ErrorCode.TODO_NOT_FOUND);
            case 1:
                if (todoResponseDtoList.get(0).isComplete()) {
                    achievementResponseDtoList.add(AchievementResponseDto.builder()
                            .totalCnt(todoResponseDtoList.get(0).getCount())
                            .completeCnt(todoResponseDtoList.get(0).getCount())
                            .achievementRate(100)
                            .addDate(todoResponseDtoList.get(0).getAddDate())
                            .build());
                    return achievementResponseDtoList;

                } else {
                    achievementResponseDtoList.add(AchievementResponseDto.builder()
                            .totalCnt(todoResponseDtoList.get(0).getCount())
                            .completeCnt(0)
                            .achievementRate(0)
                            .addDate(todoResponseDtoList.get(0).getAddDate())
                            .build());
                    return achievementResponseDtoList;
                }
        }
        long totalCnt = 0;
        float percent = 0;
        for (int i = 0; i < todoResponseDtoList.size(); i++) {
            if (todoResponseDtoList.get(i).getAddDate().isEqual(todoResponseDtoList.get(i + 1).getAddDate())) {
                totalCnt = todoResponseDtoList.get(i).getCount() + todoResponseDtoList.get(i + 1).getCount();
                percent = (float) todoResponseDtoList.get(i + 1).getCount() / totalCnt;
                achievementResponseDtoList.add(AchievementResponseDto.builder()
                        .totalCnt(totalCnt)
                        .completeCnt(todoResponseDtoList.get(i + 1).getCount())
                        .achievementRate(Math.round(percent * 10000) / 100.0)
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
                            .build());
                } else {
                    achievementResponseDtoList.add(AchievementResponseDto.builder()
                            .totalCnt(totalCnt)
                            .completeCnt(0)
                            .achievementRate(0)
                            .addDate(todoResponseDtoList.get(i).getAddDate())
                            .build());
                }
            }

        }

        return achievementResponseDtoList;
    }

    //한 주차에 TRUE or FALSE만 존재할 경우 indexOutOfBound 에러 발생
    //중간에 주차가 모두 비어있을 경우는 0%로 반환
    public List<AchievementResponseDto> getWeeklyAchievementRate(UserDetailsImpl userDetailsImpl) {
        Member member = userDetailsImpl.getMember();
//        if (!todoRepository.existsByMember(member))
//            throw new CustomException(ErrorCode.TODO_NOT_FOUND);   //가짜데이터라 주석처리
        TodoDateResponseDto todoDateResponseDto = todoRepository.getFirstandLastTodoAddDate(member);
        LocalDate firstTodoAddDate = todoDateResponseDto.getFirstTodoAddDate();
        LocalDate lastTodoAddDate = todoDateResponseDto.getLastTodoAddDate();
        FirstWeekResponseDto firstWeekResponseDto = timeCustom.getDayOfWeek(firstTodoAddDate);
        LocalDate startDate = firstWeekResponseDto.getStartDate();
        LocalDate endDate = firstWeekResponseDto.getEndDate();

        List<AchievementResponseDto> achievementResponseDtoList = new ArrayList<>();
        do {
            List<TodoResponseDto> todoResponseDtoList = achievementRepository.getWeeklyAchievementRate(startDate, endDate, member);
            long totalCnt;
            float percent;
            if (todoResponseDtoList.size() == 2) {
                totalCnt = todoResponseDtoList.get(0).getCount() + todoResponseDtoList.get(1).getCount();
                percent = (float) todoResponseDtoList.get(1).getCount() / totalCnt;

                achievementResponseDtoList.add(AchievementResponseDto.builder()
                        .totalCnt(totalCnt)
                        .completeCnt(todoResponseDtoList.get(1).getCount())
                        .achievementRate(Math.round(percent * 10000) / 100.0)
                        .build());
            } else if (todoResponseDtoList.size() == 1) {
                totalCnt = todoResponseDtoList.get(0).getCount();
                if (todoResponseDtoList.get(0).isComplete()) {
                    achievementResponseDtoList.add(AchievementResponseDto.builder()
                            .totalCnt(totalCnt)
                            .completeCnt(totalCnt)
                            .achievementRate(100)
                            .build());
                } else {
                    achievementResponseDtoList.add(AchievementResponseDto.builder()
                            .totalCnt(totalCnt)
                            .completeCnt(0)
                            .achievementRate(0)
                            .build());
                }
            } else {
                achievementResponseDtoList.add(null);
            }

            if (endDate.equals(lastTodoAddDate))
                break;

            startDate = endDate.plusDays(1);
            endDate = endDate.plusDays(7);
        } while (lastTodoAddDate.isAfter(startDate));
        return achievementResponseDtoList;
    }

    public AchievementResponseDto getTotalAchievementRate(UserDetailsImpl userDetailsImpl) {
        Member member = userDetailsImpl.getMember();

        AchievementResponseDto achievementResponseDto = achievementRepository.getTotalAchievementRate(member);
        if (!achievementRepository.existsByNickname(member.getNickname()))
            throw new CustomException(ErrorCode.TODO_NOT_FOUND);
        return AchievementResponseDto.builder()
                .plannerCnt(achievementResponseDto.getPlannerCnt())
                .achievementRate(achievementResponseDto.getAchievementRate() / achievementResponseDto.getPlannerCnt())
                .build();
//        List<TodoResponseDto>  todoResponseDtoList = achievementRepository.getTotalAchievementRate(member);
//        switch (todoResponseDtoList.size()) {
//            case 0:
//                throw new CustomException(ErrorCode.TODO_NOT_FOUND);
//            case 1:
//                if (todoResponseDtoList.get(0).isComplete()) {
//                    return AchievementResponseDto.builder()
//                            .totalCnt(todoResponseDtoList.get(0).getCount())
//                            .completeCnt(todoResponseDtoList.get(0).getCount())
//                            .achievementRate(100)
//                            .build();
//                } else {
//                    return AchievementResponseDto.builder()
//                            .totalCnt(todoResponseDtoList.get(0).getCount())
//                            .completeCnt(0)
//                            .achievementRate(0)
//                            .build();
//                }
//        }
//        long totalCnt = todoResponseDtoList.get(0).getCount() + todoResponseDtoList.get(1).getCount();
//        float percent = (float) todoResponseDtoList.get(1).getCount() / totalCnt;
//        return AchievementResponseDto.builder()
//                .totalCnt(totalCnt)
//                .completeCnt(todoResponseDtoList.get(1).getCount())
//                .achievementRate(Math.round(percent * 10000) / 100.0)
//                .build();

    }

    public void saveDaylyAchievement() {

        LocalDate yesterDay = timeCustom.currentDate().minusDays(1);

        List<AchievementResponseDto> achievementResponseDtoList = new ArrayList<>();
        List<Achievement> achievementList = new ArrayList<>();

        List<TodoResponseDto> todoResponseDtoList = achievementRepository.getRecentlyRate(yesterDay);
        int todoResponseDtoListSize = todoResponseDtoList.size();
        //데이터가 없거나 true or false로 하나만 있을 경우
        switch (todoResponseDtoListSize) {
            case 0:
                throw new CustomException(ErrorCode.TODO_NOT_FOUND);
            case 1:
                if (todoResponseDtoList.get(0).isComplete()) {
                    achievementResponseDtoList.add(AchievementResponseDto.builder()
                            .totalCnt(todoResponseDtoList.get(0).getCount())
                            .completeCnt(todoResponseDtoList.get(0).getCount())
                            .achievementRate(100)
                            .nickname(todoResponseDtoList.get(0).getNickname())
                            .addDate(todoResponseDtoList.get(0).getAddDate())
                            .build());
                    Achievement achievement = Achievement.builder()
                            .nickname(achievementResponseDtoList.get(0).getNickname())
                            .addDate(achievementResponseDtoList.get(0).getAddDate())
                            .score(achievementResponseDtoList.get(0).getAchievementRate())
                            .build();

                    achievementRepository.save(achievement);
                } else {
                    achievementResponseDtoList.add(AchievementResponseDto.builder()
                            .totalCnt(todoResponseDtoList.get(0).getCount())
                            .completeCnt(0)
                            .achievementRate(0)
                            .nickname(todoResponseDtoList.get(0).getNickname())
                            .addDate(todoResponseDtoList.get(0).getAddDate())
                            .build());
                    Achievement achievement = Achievement.builder()
                            .nickname(achievementResponseDtoList.get(0).getNickname())
                            .addDate(achievementResponseDtoList.get(0).getAddDate())
                            .score(achievementResponseDtoList.get(0).getAchievementRate())
                            .build();

                    achievementRepository.save(achievement);
                }
        }
        long totalCnt = 0;
        float percent = 0;
        if (todoResponseDtoListSize > 1) {
            for (int i = 0; i < todoResponseDtoListSize; i++) {
                //i<todoResponseDtoListSize-1 는 마지막 하나 남았을 경우 true or false만 존재하는 경우이기 때문에 조건문에서 IndexOutOfBount에러 발생
                if (i < todoResponseDtoListSize - 1 && todoResponseDtoList.get(i).getAddDate().isEqual(todoResponseDtoList.get(i + 1).getAddDate()) &&
                        todoResponseDtoList.get(i).getNickname().equals(todoResponseDtoList.get(i + 1).getNickname())) {
                    totalCnt = todoResponseDtoList.get(i).getCount() + todoResponseDtoList.get(i + 1).getCount();
                    percent = (float) todoResponseDtoList.get(i + 1).getCount() / totalCnt;
                    achievementResponseDtoList.add(AchievementResponseDto.builder()
                            .totalCnt(totalCnt)
                            .completeCnt(todoResponseDtoList.get(i + 1).getCount())
                            .achievementRate(Math.round(percent * 10000) / 100.0)
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
            achievementList.add(Achievement.builder()
                    .nickname(achievementResponseDto.getNickname())
                    .addDate(achievementResponseDto.getAddDate())
                    .score(achievementResponseDto.getAchievementRate())
                    .build());
        }
        achievementRepository.saveAll(achievementList);
    }


}
