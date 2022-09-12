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

    @GetMapping("/api/todo/achievement/today")
    public ResponseEntity<AchievementResponseDto> getAchievementRate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return ResponseEntity.ok()
                .body(achievementService.getAchievementRate(userDetailsImpl));
    }

    @GetMapping("/api/todo/achievement")
    public ResponseEntity<List<AchievementResponseDto>> getDaylyAchievementRate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return ResponseEntity.ok()
                .body(achievementService.getDaylyAchievementRate(userDetailsImpl));
    }
    @GetMapping("/api/todo/achievement/weekly")
    public ResponseEntity<List<AchievementResponseDto>> getWeeklyAchievementRate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return ResponseEntity.ok()
                .body(achievementService.getWeeklyAchievementRate(userDetailsImpl));
    }
    @GetMapping("/api/todo/achievement/total")
    public ResponseEntity<AchievementResponseDto> getTotalAchievementRate(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return ResponseEntity.ok()
                .body(achievementService.getTotalAchievementRate(userDetailsImpl));
    }
    @PostMapping("/api/todo/achievement")
    public void saveDaylyAchievement() {
        achievementService.saveDaylyAchievement();
    }
}
