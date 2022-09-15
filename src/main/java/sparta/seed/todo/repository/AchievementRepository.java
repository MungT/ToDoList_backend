package sparta.seed.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.seed.todo.domain.Achievement;

public interface AchievementRepository extends JpaRepository<Achievement,Long>,AchievementRepositoryCustom {
    boolean existsByNickname(String nickname);
}
