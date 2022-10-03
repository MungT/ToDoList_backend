package sparta.seed.follow.repository;

import sparta.seed.follow.dto.FollowResponseDto;
import sparta.seed.login.domain.Member;

import java.util.List;

public interface FollowRepositoryCustom {
    List<Member> getFollowingList(Member member);
    List<Member> getFollowerList(Member member);

}
