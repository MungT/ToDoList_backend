package sparta.seed.follow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sparta.seed.exception.CustomException;
import sparta.seed.exception.ErrorCode;
import sparta.seed.follow.domain.Follow;
import sparta.seed.follow.dto.FindResponseDto;
import sparta.seed.follow.repository.FollowRepository;
import sparta.seed.follow.dto.FollowRequestDto;
import sparta.seed.login.domain.Member;

import sparta.seed.login.dto.MemberResponseDto;

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

}




