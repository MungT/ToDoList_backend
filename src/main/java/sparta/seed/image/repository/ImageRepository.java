package sparta.seed.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.seed.image.domain.Image;
import sparta.seed.login.domain.Member;

public interface ImageRepository extends JpaRepository<Image, Long>, ImageRepositoryCustom {

    boolean existsByMember(Member member);
}
