package sparta.seed.todo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import sparta.seed.login.domain.Authority;

import javax.persistence.*;
import java.sql.Timestamp;

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

  @Builder
  public Todo(Long id, String content, Boolean isComplete, String addDate) {
    this.id = id;
    this.content = content;
    this.isComplete = isComplete;
    this.addDate = addDate;
  }
}