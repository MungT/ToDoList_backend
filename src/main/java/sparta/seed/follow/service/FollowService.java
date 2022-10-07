package sparta.seed.follow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.seed.exception.CustomException;
import sparta.seed.exception.ErrorCode;
import sparta.seed.follow.domain.Follow;
import sparta.seed.follow.dto.FollowResponseDto;
import sparta.seed.follow.repository.FollowRepository;
import sparta.seed.login.domain.Member;
import sparta.seed.login.repository.MemberRepository;
import sparta.seed.message.Message;
import sparta.seed.sercurity.UserDetailsImpl;

@RequiredArgsConstructor
@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;


    // 팔로우 & 팔로우 취소 기능

    public String upDownFollow(Long toMemberId, UserDetailsImpl userDetailsImpl) {
        //로그인한 유저
        Member fromMember = memberRepository.findById(userDetailsImpl.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        //팔로우 할 유저
        Member toMember = memberRepository.findById(toMemberId).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        // 코드 리뷰 후, 해당 코드 상단 이동

        if (fromMember.equals(toMember)) {
            throw new CustomException(ErrorCode.DONT_FOLLOW_MYSELF);
        }
        // DB에 해당 객체가 없다면 ( 저장되어 있지 않은 값이라면 ) -> 팔로우 진행
        if (!followRepository.existsByFromMemberAndToMember(fromMember, toMember)) {
            Follow follow = new Follow(fromMember, toMember);
            followRepository.save(follow);
            // 추후 메세지 OR 에러로 변경
            return Message.FOLLOW_SUCCESS.getMessage();
        } else {
            // DB에 해당 객체 값이 있다면 -> 팔로우 취소
            followRepository.deleteByFromMemberAndToMember(fromMember, toMember);
            return Message.UNFOLLOW_SUCCESS.getMessage();

        }
    }
    public FollowResponseDto getFollowCnt(Long memberId) {
        // 접속한 유저 기준 팔로잉한 수
        int followingCnt = followRepository.countToMemberIdByFromMemberId(memberId);
        // 접속한 유저 기준 자신을 팔로워한 수
        int followerCnt = followRepository.countFromMemberIdByToMemberId(memberId);
        return FollowResponseDto.builder()
                .followingCnt(followingCnt)
                .followerCnt(followerCnt)
                .build();
    }
}




