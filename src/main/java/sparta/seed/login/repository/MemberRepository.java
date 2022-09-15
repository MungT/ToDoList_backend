package sparta.seed.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.seed.login.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
  Optional<Member> findByUsername(String username);

  Optional<Member> findBySocialId(String id);
  Optional<Member> findByNickname(String nickname);
}
