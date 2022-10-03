package sparta.seed.follow.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import sparta.seed.follow.domain.QFollow;
import sparta.seed.follow.dto.FollowResponseDto;
import sparta.seed.follow.dto.QFollowResponseDto;
import sparta.seed.login.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;

import static sparta.seed.follow.domain.QFollow.follow;


public class FollowRepositoryImpl implements FollowRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public FollowRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Member> getFollowingList(Member member){
        return queryFactory
                .select(follow.toMember)
                .from(follow)
                .where(follow.fromMember.eq(member))
                .fetch();
    }
    public List<Member> getFollowerList(Member member){
        return queryFactory
                .select(follow.fromMember)
                .from(follow)
                .where(follow.toMember.eq(member))
                .fetch();

    }
}