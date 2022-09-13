package sparta.seed.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sparta.seed.todo.domain.Rank;
import sparta.seed.todo.dto.AchievementResponseDto;
import sparta.seed.todo.repository.RankRepository;
import sparta.seed.todo.service.RankService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class RankController {

    private final RankService rankService;
    private final RankRepository rankRepository;

    @PostMapping("/api/rank/weekly")
    public ResponseEntity<List<Rank>> saveWeeklyRank() {
        return ResponseEntity.ok()
                .body(rankService.saveWeeklyRank());
    }
    @PostMapping("/api/rank/monthly")
    public ResponseEntity<List<Rank>> saveMonthlyRank() {
        return ResponseEntity.ok()
                .body(rankService.saveMonthlyRank());
    }
    @GetMapping("/api/rank/weekly")
    public Slice<AchievementResponseDto> getWeeklyPage(Pageable pageable) {
        return rankRepository.getWeeklyPage(pageable);
    }
//    @GetMapping("/api/rank/monthly")
//    public Slice<AchievementResponseDto> getMonthlyPage(Pageable pageable) {
//        return rankRepository.getMonthlyPage(pageable);
//    }

}
