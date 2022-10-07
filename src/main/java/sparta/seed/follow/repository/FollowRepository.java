package sparta.seed.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.seed.follow.domain.Follow;
import sparta.seed.login.domain.Member;

import java.util.List;
import java.util.Optional;


public interface FollowRepository extends JpaRepository<Follow, Long>, FollowRepositoryCustom {

    List<Follow> findAllByToMember(Member member);

    int countToMemberIdByFromMemberId(Long fromMemberId);
    int countFromMemberIdByToMemberId(Long fromMemberId);

    Boolean existsByFromMemberAndToMember(Member fromMember, Member toMember);
    void deleteByFromMemberAndToMember(Member fromMember, Member toMember);

}
