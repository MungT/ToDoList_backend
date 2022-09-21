package sparta.seed.follow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.seed.exception.CustomException;
import sparta.seed.exception.ErrorCode;
import sparta.seed.follow.domain.Follow;
import sparta.seed.follow.repository.FollowRepository;
import sparta.seed.follow.dto.FollowRequestDto;
import sparta.seed.follow.dto.ResponseDto;
import sparta.seed.login.domain.Member;
import sparta.seed.login.repository.MemberRepository;
import sparta.seed.message.Message;
import sparta.seed.sercurity.UserDetailsImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public String upDownFollow(Long tomemberId, UserDetailsImpl userDetailsImpl) {
        // 로그인한 유저 = 팔로우 하는 유저
        Member fromMember = userDetailsImpl.getMember();

        // 팔로우 대상 유저 / DB에 없으면 에러로 반환
        Member toMember = memberRepository.findById(tomemberId).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Optional<Follow> findFollowing = followRepository.findByFromMemberAndToMember_Id(fromMember, tomemberId);

        if (fromMember.getId().equals(tomemberId)) {
            //throw new CustomException(ErrorCode.DUPLE_NICK);
            throw new CustomException(ErrorCode.MYSELF_NOT_SELECTED);
        }

        if (!findFollowing.isPresent()) {
            FollowRequestDto followRequestDto = new FollowRequestDto(fromMember, toMember);
            Follow follow = new Follow(followRequestDto);
            followRepository.save(follow);
            // 추후 메세지 OR 에러로 변경
            // 반환형 String / Message.MYMOTTO_UPDATE_SUCCESS.getMessage();
            return Message.MYFOLLOW_UPDATE_SUCCESS.getMessage();
        } else {
            followRepository.deleteById(findFollowing.get().getId());
            // 추후 메세지 OR 에러로 변경
            //throw new CustomException(ErrorCode.DUPLE_NICK);
            return Message.MYFOLLOW_UPDATE_CANCEL.getMessage();
        }
    }
    public List<Member> getFollows(UserDetailsImpl userDetailsImpl) {
        // 전체 조회
        List<Follow> list = followRepository.findAll();
        // 접속자 생성
        Long fromMember = userDetailsImpl.getId();
        List<Member> temp = new ArrayList<>();

        for (Follow follow : list) {
            // 접속자와 전체조회에서 getfrommember가 같으면
            if (fromMember.equals(follow.getFromMember().getId())) {
                temp.add(follow.getToMember());
            }
            if (temp.isEmpty()) {
                throw new CustomException(ErrorCode.FOLLOWLIST_EMPTY);
            }
        }
        return temp;
    }
}




