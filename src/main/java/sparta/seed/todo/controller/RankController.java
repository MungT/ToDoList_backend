package sparta.seed.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sparta.seed.sercurity.UserDetailsImpl;
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

    //(서버)주간 랭킹 저장
    @Transactional
    @PostMapping("/api/rank/weekly")
    public ResponseEntity<List<Rank>> saveWeeklyRank() {
        return ResponseEntity.ok()
                .body(rankService.saveWeeklyRank());
    }
    //(서버)월간 랭킹 저장
    @PostMapping("/api/rank/monthly")
    public ResponseEntity<List<Rank>> saveMonthlyRank() {
        return ResponseEntity.ok()
                .body(rankService.saveMonthlyRank());
    }
    //이번 주 랭킹 페이지 조회
    @GetMapping("/api/rank/weekly")
    public ResponseEntity<Slice<AchievementResponseDto>> getWeeklyPage(Pageable pageable) {
        return ResponseEntity.ok(rankRepository.getWeeklyPage(pageable));
    }
    //월간 랭킹 페이지 조회
    @GetMapping("/api/rank/monthly")
    public ResponseEntity<Slice<AchievementResponseDto>> getMonthlyPage(Pageable pageable) {
        return ResponseEntity.ok(rankRepository.getMonthlyPage(pageable));
    }
    //유저의 지난 주 랭킹 조회
    @GetMapping("/api/rank/lastweek/member")
    public ResponseEntity<Rank> getLastweekRank(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        String nickname = userDetailsImpl.getNickname();
        return ResponseEntity.ok(rankService.getLastweekRank(nickname));
    }
    //유저의 주간 랭킹 조회
    @GetMapping("/api/rank/weekly/member")
    public ResponseEntity<Rank> getMonthlyRank(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        String nickname = userDetailsImpl.getNickname();
        return ResponseEntity.ok(rankRepository.getWeeklyRank(nickname));
    }
    //유저의 월간 랭킹 조회
    @GetMapping("/api/rank/monthly/member")
    public ResponseEntity<Rank> getWeeklyRank(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        String nickname = userDetailsImpl.getNickname();
        return ResponseEntity.ok(rankRepository.getMonthlyRank(nickname));
    }
}
