package sparta.seed.follow.domain;

import lombok.*;
import sparta.seed.follow.dto.FollowRequestDto;
import sparta.seed.login.domain.Member;

import javax.persistence.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Member fromMember; // 구독을 하는 유저

    @ManyToOne
    @JoinColumn
     private Member toMember; // 구독 받는 유저


    @Builder
    public Follow(FollowRequestDto requestDto) {
        this.fromMember = requestDto.getFromMember();
        this.toMember = requestDto.getToMember();
    }
}
