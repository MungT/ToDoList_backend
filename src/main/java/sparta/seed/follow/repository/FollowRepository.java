package sparta.seed.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.seed.follow.domain.Follow;
import sparta.seed.login.domain.Member;

import java.util.List;
import java.util.Optional;


public interface FollowRepository extends JpaRepository<Follow, Long> {

    Optional<Follow> findByFromMemberAndToMember_Id(Member member, Long postId);

    List<Follow> findAllByFromMember(Member member);
    List<Follow> findAllByToMember(Member member);

    int countToMemberIdByFromMemberId(Long fromMemberId);
    int countFromMemberIdByToMemberId(Long fromMemberId);


}
