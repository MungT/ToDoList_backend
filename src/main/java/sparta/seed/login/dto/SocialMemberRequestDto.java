package sparta.seed.login.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SocialMemberRequestDto {
    private String socialId;
    private String nickname;
    private String username;
    private String profileImage;
    private String highschool;
    private String grade;

    @Builder
    public SocialMemberRequestDto(String socialId, String nickname, String username, String profileImage, String highschool, String grade) {
        this.socialId = socialId;
        this.nickname = nickname;
        this.username = username;
        this.profileImage = profileImage;
        this.highschool = highschool;
        this.grade = grade;
    }
}