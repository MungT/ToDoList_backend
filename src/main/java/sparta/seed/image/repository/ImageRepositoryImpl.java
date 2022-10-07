package sparta.seed.image.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import sparta.seed.image.domain.QImage;
import sparta.seed.image.dto.ImageResponseDto;
import sparta.seed.image.dto.QImageResponseDto;

import javax.persistence.EntityManager;
import java.util.List;

import static sparta.seed.image.domain.QImage.image;

public class ImageRepositoryImpl implements ImageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ImageRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<ImageResponseDto> getBoastImage(Long memberId) {
        return queryFactory
                .select(new QImageResponseDto(image.id, image.imgUrl))
                .from(image)
                .where(image.member.id.eq(memberId))
                .orderBy(image.id.asc())
                .fetch();
    }
}
