package sparta.seed.todo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import sparta.seed.login.domain.Authority;
import sparta.seed.login.domain.Member;
import sparta.seed.todo.dto.TodoRequestDto;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class Todo {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String content;
  private Boolean isComplete;

  @CreationTimestamp
  private Timestamp createdAt;

  private String addDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  private Member member;

  @Builder
  public Todo(Long id, String content, Boolean isComplete, String addDate, Member member) {
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