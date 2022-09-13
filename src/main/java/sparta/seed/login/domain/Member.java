package sparta.seed.login.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Member {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;

  private String password;

  private String nickname;

  private String socialId;

  @Enumerated(EnumType.STRING)
  private Authority authority;



  private String profileImage;

  private String highschool;
  private String grade;
  private String myMotto;

  @Builder
  public Member(Long id, String username, String password, String nickname, String socialId, Authority authority, String profileImage, String highschool, String grade, String myMotto) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.nickname = nickname;
    this.socialId = socialId;
    this.authority = authority;
    this.profileImage = profileImage;
    this.highschool = highschool;
    this.grade = grade;
    this.myMotto = myMotto;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public void setHighschool(String highschool) {
    this.highschool = highschool;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  public void setMyMotto(String myMotto) {
    this.myMotto = myMotto;
  }

  public void setProfileImage(String profileImage) {
    this.profileImage = profileImage;
  }

}