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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = true) //가짜데이터 쓰는동안만 true
    private Member member;

    @Builder
    public Todo(Long id, String content, Boolean isComplete, LocalDate addDate, Member member) {
        this.id = id;
        this.content = content;
        this.isComplete = isComplete;
        this.addDate = addDate;
        this.member = member;
    }

    public void update(TodoRequestDto todoRequestDto) {
        this.content = todoRequestDto.getContent();
        this.isComplete = todoRequestDto.getIsComplete();
    }
}