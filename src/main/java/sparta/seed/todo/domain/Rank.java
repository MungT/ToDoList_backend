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
@NoArgsConstructor
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDate addDate;
    @Column(nullable = false)
    private double score;

    @Column(nullable = false)
    private String nickname;

    @Builder
    public Rank(String nickname, LocalDate addDate, double score) {
        this.addDate = addDate;
        this.score = score;
        this.nickname = nickname;
    }
}
