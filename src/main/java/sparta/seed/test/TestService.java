package sparta.seed.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {
    public final TestRepository testRepository;

    public String testPost(TestDto testDto) {
        Test test = new Test(testDto);
        testRepository.save(test);
        return "성공";
    }
}
