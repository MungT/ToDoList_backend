package sparta.seed.todo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String nickname;

    @Builder
    public Category(Long id, String title, String nickname) {
        this.id = id;
        this.title = title;
        this.nickname = nickname;
    }
}
