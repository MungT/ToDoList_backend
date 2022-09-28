package sparta.seed.todo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.seed.login.domain.Member;
import sparta.seed.todo.dto.TodoRequestDto;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Table(name = "user_rank")
@NoArgsConstructor
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int ranking;
//    @Column(nullable = false)
//    private LocalDate addDate;
    @Column(nullable = false)
    private double score;

    @Column(nullable = false)
    private String nickname;

    private String category;

    @Builder
    public Rank(Long id, int ranking, String nickname,double score, String category) {
        this.id = id;
        this.ranking = ranking;
        this.score = score;
        this.nickname = nickname;
        this.category = category;
    }
}
