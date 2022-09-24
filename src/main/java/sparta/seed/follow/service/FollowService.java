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
    // 팔로잉 리스트
    // 수정 : 닉네임과 이미지만 전송
//    public List<FindResponseDto> getFollowings(UserDetailsImpl userDetailsImpl) {
//        // 팔로잉 리스트 조회
//        List<Member> FollowingList = followRepository.getFollowingList(userDetailsImpl.getMember());
//        // 접속자 생성
//        List<FindResponseDto> temp = new ArrayList<>();
//
//        for (Follow follow : list) {
//            FindResponseDto findResponseDto = FindResponseDto.builder()
//                    .findMemberId(follow.getToMember().getId())
//                    .nickname(follow.getToMember().getNickname())
//                    .profile(follow.getToMember().getProfileImage())
//                    .build();
//
//            temp.add(findResponseDto);
//        }
//        return temp;
//    }

    // 팔로우 리스트
//    public List<FindResponseDto> getFollowers(UserDetailsImpl userDetailsImpl) {
//        List<Follow> list = followRepository.findAllByToMember(userDetailsImpl.getMember());
//
//        List<FindResponseDto> temp = new ArrayList<>();
//
//        for (Follow follow : list) {
//            FindResponseDto findResponseDto = FindResponseDto.builder()
//                    .findMemberId(follow.getFromMember().getId())
//                    .nickname(follow.getFromMember().getNickname())
//                    .profile(follow.getFromMember().getProfileImage())
//                    .build();
//
//            temp.add(findResponseDto);
//        }
//        return temp;
//    }
}




