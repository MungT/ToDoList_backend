package sparta.seed.todo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Achievement {
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
    public Achievement(String nickname, LocalDate addDate, double score) {
        this.addDate = addDate;
        this.score = score;
        this.nickname = nickname;
    }
}
