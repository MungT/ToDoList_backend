package sparta.seed.todo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.seed.login.domain.Member;
import sparta.seed.todo.dto.TodoRequestDto;
import sparta.seed.util.Timestamped;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Todo extends Timestamped {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private Boolean isComplete;

    @Column(nullable = false)
    private LocalDate addDate;
    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String category;

    @Builder
    public Todo(Long id, String content, Boolean isComplete, LocalDate addDate, String nickname, String category) {
        this.id = id;
        this.content = content;
        this.isComplete = isComplete;
        this.addDate = addDate;
        this.nickname = nickname;
        this.category = category;
    }

    public void update(TodoRequestDto todoRequestDto) {
        this.content = todoRequestDto.getContent();
        this.isComplete = todoRequestDto.getIsComplete();
    }
}