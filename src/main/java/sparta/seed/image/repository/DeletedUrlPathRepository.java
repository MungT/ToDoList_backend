package sparta.seed.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.seed.image.domain.DeletedUrlPath;

public interface DeletedUrlPathRepository extends JpaRepository<DeletedUrlPath, Long> {
}
