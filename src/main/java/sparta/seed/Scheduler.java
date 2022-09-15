package sparta.seed;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sparta.seed.todo.service.AchievementService;

@Component
@RequiredArgsConstructor
public class Scheduler {
    private final AchievementService achievementService;

    @Scheduled(cron = "0 0 5 * * *")
    public void saveDaylyAchievement() {
        achievementService.saveDaylyAchievement();
    }
}
