package sparta.seed.follow.repository;

import sparta.seed.login.domain.Member;
import sparta.seed.login.dto.MemberResponseDto;

import java.util.List;

public interface FollowRepositoryCustom {
    List<Member> getFollowingList(Member member);
    List<Member> getFollowerList(Member member);
}
