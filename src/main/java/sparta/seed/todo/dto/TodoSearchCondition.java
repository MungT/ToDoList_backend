package sparta.seed.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TodoSearchCondition {
    //회원명, 팀명, 나이(ageGoe, ageLoe)
    private String nickname;

    private Timestamp searchStartDate;

    private Timestamp searchEndDate;
}