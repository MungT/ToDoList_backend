package sparta.seed.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.seed.todo.domain.Rank;

public interface RankRepository extends JpaRepository<Rank,Long>, RankRepositoryCustom {
}
