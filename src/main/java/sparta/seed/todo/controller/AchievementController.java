package sparta.seed.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sparta.seed.sercurity.UserDetailsImpl;
import sparta.seed.todo.dto.AchievementResponseDto;
import sparta.seed.todo.service.AchievementService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class AchievementController {
    private final AchievementService achievementService;

    //플래너에서 해당 날짜의 달성률 반환
    @GetMapping("/api/todo/achievement")
    public ResponseEntity<AchievementResponseDto> getAchievementRate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return ResponseEntity.ok()
                .body(achievementService.getAchievementRate(userDetailsImpl));
    }
    //최근 30일 각 날짜에 해당하는 투두리스트 달성률 리스트 반환(잔디 심기)
    @GetMapping("/api/todo/achievement/dayly")
    public ResponseEntity<List<AchievementResponseDto>> getDaylyAchievementRate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return ResponseEntity.ok()
                .body(achievementService.getDaylyAchievementRate(userDetailsImpl));
    }
    //유저 주간 달성률 조회
    @GetMapping("/api/todo/achievement/weekly")
    public ResponseEntity<List<AchievementResponseDto>> getWeeklyAchievementRate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return ResponseEntity.ok()
                .body(achievementService.getWeeklyAchievementRate(userDetailsImpl));
    }
    //유저 총 달성률 조회
    @GetMapping("/api/todo/achievement/total")
    public ResponseEntity<AchievementResponseDto> getTotalAchievementRate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return ResponseEntity.ok()
                .body(achievementService.getTotalAchievementRate(userDetailsImpl));
    }
    //서버에서 achievement 테이블에 저장
    @PostMapping("/api/todo/achievement")
    public void saveDaylyAchievement() {
        achievementService.saveDaylyAchievement();
    }
}
