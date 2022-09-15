package sparta.seed.test;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class TestController {

    private final TestService testService;
    private final TestRepository testRepository;

    @GetMapping("/test")
    public String test(@RequestBody TestDto testDto) {
        return testService.testPost(testDto);
    }

    @DeleteMapping("/test/delete")
    public String testDelete() {
        testRepository.deleteAll();
        return "clear";
    }
}
