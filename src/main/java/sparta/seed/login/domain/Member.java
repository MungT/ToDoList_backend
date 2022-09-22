package sparta.seed.login.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.seed.image.domain.Image;

import javax.persistence.*;
import java.util.List;

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

  // 마이페이지 팔로우 / 팔로잉 버전
  private int followingsCnt;
  private int followersCnt;



  @OneToMany(mappedBy = "member",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  @JsonManagedReference //DB연관관계 무한회귀 방지
  private List<Image> imgList;

  @Builder
  public Member(Long id, String username, String password, String nickname, String socialId, Authority authority, String profileImage, String highschool, String grade, String myMotto,
  int followingsCnt, int followersCnt) {
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

    // 마이페이지 팔로우 / 팔로잉 버전
    this.followingsCnt = followingsCnt;
    this.followersCnt = followersCnt;
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
  public void deleteImg(Image image) {
    this.imgList.remove(image);
  }

}