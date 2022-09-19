package sparta.seed.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.seed.login.domain.School;

public interface SchoolRepository extends JpaRepository<School, Long> {

}
