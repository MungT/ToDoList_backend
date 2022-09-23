package sparta.seed.login.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.seed.image.domain.Image;
import sparta.seed.login.dto.GoalDateRequestDto;

import javax.persistence.*;
import java.time.LocalDate;
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
  private String goalTitle;
  private LocalDate goalDate;
  private int followingsCnt;
  private int followersCnt;



  @OneToMany(mappedBy = "member",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  @JsonManagedReference //DB연관관계 무한회귀 방지
  private List<Image> imgList;

  @Builder
  public Member(Long id, String username, String password, String nickname, String socialId, Authority authority, String profileImage, String highschool, String grade, String myMotto,
  String goalTitle, LocalDate goalDate, int followingsCnt, int followersCnt) {
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
    this.goalTitle = goalTitle;
    this.goalDate = goalDate;
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

  public void setGoalTitle(String goalTitle) {
    this.goalTitle = goalTitle;
  }
  public void setGoalDate(LocalDate goalDate) {
    this.goalDate = goalDate;
  }

}