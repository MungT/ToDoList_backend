package sparta.seed.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sparta.seed.exception.CustomException;
import sparta.seed.exception.ErrorCode;
import sparta.seed.jwt.TokenProvider;
import sparta.seed.login.domain.Member;
import sparta.seed.login.domain.RefreshToken;
import sparta.seed.login.dto.*;
import sparta.seed.login.repository.MemberRepository;
import sparta.seed.login.repository.RefreshTokenRepository;
import sparta.seed.message.Message;
import sparta.seed.sercurity.UserDetailsImpl;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
//    private final AuthenticationManagerBuilder authenticationManagerBuilder;
//    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public String checkNickname(String nickname) {
        if (memberRepository.findByNickname(nickname).isPresent()) {
            throw new CustomException(ErrorCode.DUPLE_NICK);
        }

        return Message.AVAILABLE_NICK.getMessage();
    }

    @Transactional
    public Member signup(SocialMemberRequestDto socialMemberRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        Member member = memberRepository.findById(userDetailsImpl.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        member.setNickname(socialMemberRequestDto.getNickname());
        member.setHighschool(socialMemberRequestDto.getHighschool());
        member.setGrade(socialMemberRequestDto.getGrade());
        System.out.println(member);
        return memberRepository.save(member);
    }
    public Member getMember(UserDetailsImpl userDetailsImpl) {
        return memberRepository.findById(userDetailsImpl.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }
    public String updateMotto(MottoRequestDto mottoRequestDto, UserDetailsImpl userDetailsImpl) {
        Member member = memberRepository.findById(userDetailsImpl.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        member.setMyMotto(mottoRequestDto.getMyMotto());
        memberRepository.save(member);
        return Message.MYMOTTO_UPDATE_SUCCESS.getMessage();
    }
    @Transactional
    public MemberResponseDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        MemberResponseDto tokenDto = tokenProvider.generateTokenDto(authentication);

//        // 6. 저장소 정보 업데이트
//        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
//        refreshTokenRepository.save(newRefreshToken);

        // 토큰 발급
        return tokenDto;
    }



}
