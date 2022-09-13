package sparta.seed.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.seed.image.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Long>, ImageRepositoryCustom {
}
