package sparta.seed.school.domain;

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
    private String address;

    @Builder
    public School(Long id, String schoolName, String address) {
        this.id = id;
        this.schoolName = schoolName;
        this.address = address;
    }
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}