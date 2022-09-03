package sparta.seed.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.seed.exception.CustomException;
import sparta.seed.exception.ErrorCode;
import sparta.seed.login.domain.Member;
import sparta.seed.login.dto.SocialMemberRequestDto;
import sparta.seed.login.repository.MemberRepository;
import sparta.seed.message.Message;
import sparta.seed.sercurity.UserDetailsImpl;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    public String checkNickname(String nickname) {
        if(memberRepository.findByNickname(nickname).isPresent()){
            throw new CustomException(ErrorCode.DUPLE_NICK);
        }

        return Message.AVAILABLE_NICK.getMessage();
    }


    public Member signup(SocialMemberRequestDto socialMemberRequestDto, UserDetailsImpl userDetailsImpl) {
        Member member = userDetailsImpl.getMember();
        member.setNickname(socialMemberRequestDto.getNickname());
        member.setHighschool(socialMemberRequestDto.getHighschool());
        member.setGrade(socialMemberRequestDto.getGrade());

        return memberRepository.save(member);
    }
}
