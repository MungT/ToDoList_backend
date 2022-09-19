package sparta.seed.login.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String schoolName;

    @Builder
    public School(String schoolName) {
        this.schoolName = schoolName;
    }
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}