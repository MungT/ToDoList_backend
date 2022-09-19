package sparta.seed.login.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private Long id;
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;
    private String nickname;
    private String username;
    private String socialId;
    private String profileImage;

    @Builder
    public MemberResponseDto(Long id, String grantType, String accessToken, String refreshToken, Long accessTokenExpiresIn, String nickname, String username, String socialId, String profileImage) {
        this.id = id;
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
        this.nickname = nickname;
        this.username = username;
        this.socialId = socialId;
        this.profileImage = profileImage;
    }
}

