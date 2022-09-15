package sparta.seed.image.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static sparta.seed.image.domain.QImage.image;

public class ImageRepositoryImpl implements ImageRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public ImageRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<String> getBoastImage(Long memberId) {
        return queryFactory
                .select(image.imgUrl)
                .from(image)
                .where(image.member.id.eq(memberId))
                .orderBy(image.id.asc())
                .fetch();
    }
}
