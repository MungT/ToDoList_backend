//package sparta.seed.util;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class Scheduler {
//    private final ArticleService articleService;
//
//    @Scheduled(cron = "0 0 1 * * *")
//    public void removeImage() {
//        log.info("S3이미지 삭제 완료");
//        articleService.removeS3Image();
//    }
//}
