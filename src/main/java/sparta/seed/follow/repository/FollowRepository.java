package sparta.seed.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.seed.follow.domain.Follow;
import sparta.seed.login.domain.Member;

import java.util.Optional;


public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFromMemberAndToMember_Id(Member member, Long postId);
}
