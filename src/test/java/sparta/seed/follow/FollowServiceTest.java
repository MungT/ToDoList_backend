package sparta.seed.follow;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import sparta.seed.follow.service.FollowService;
import sparta.seed.login.domain.Member;
import sparta.seed.login.dto.SocialMemberRequestDto;
import sparta.seed.login.repository.MemberRepository;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class FollowServiceTest {

    @Autowired MemberRepository memberRepository;
    @Autowired
    FollowService followService;

    @Test
    public void 회원가입() throws Exception{
    //세팅
        SocialMemberRequestDto member1 = new SocialMemberRequestDto();
    member1.setNickname("팔로우 하는 놈");
        SocialMemberRequestDto member2 = new SocialMemberRequestDto();
    member2.setNickname("팔로우 당하는 놈");

    //로직 실행
      Member 인간1 = signup(member1);
       Member 인간2 = signup(member2);
    //결과
        assertEquals(member1.getNickname(),인간1.getNickname());
        assertEquals(member2.getNickname(),인간2.getNickname());
    }

    @Test
    public void 팔로우() throws Exception{
    //세팅

    //로직 실행

    //결과


    }

    public Member signup(SocialMemberRequestDto socialMemberRequestDto) {
        Member member = new Member();
        member.setNickname(socialMemberRequestDto.getNickname());
        member.setHighschool(socialMemberRequestDto.getHighschool());
        member.setGrade(socialMemberRequestDto.getGrade());
        System.out.println(member);
        return memberRepository.save(member);
    }
}