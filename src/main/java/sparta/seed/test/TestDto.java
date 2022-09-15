package sparta.seed.test;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TestDto {
    private String name;

    public TestDto(String name) {
        this.name = name;
    }


}
