package sparta.seed.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.seed.school.domain.School;

public interface SchoolRepository extends JpaRepository<School, Long>, SchoolRepositoryCustom {

}
