package sparta.seed;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sparta.seed.image.service.ImageService;
import sparta.seed.todo.service.AchievementService;
import sparta.seed.todo.service.RankService;

@Component
@RequiredArgsConstructor
public class Scheduler {
    private final AchievementService achievementService;
    private final ImageService imageService;
    private final RankService rankService;
    @Scheduled(cron = "0 0 5 * * *")
    public void saveDaylyAchievementAndRemoveImage() {
        achievementService.saveDaylyAchievement();
        rankService.saveWeeklyRank();
        rankService.saveMonthlyRank();
        imageService.removeS3Image();
    }


}
