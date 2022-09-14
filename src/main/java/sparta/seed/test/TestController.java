package sparta.seed.test;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "*")
public class TestController {

    private final TestService testService;

    @GetMapping("/test")
    public String test(@RequestBody TestDto testDto) {
        return testService.testPost(testDto);
    }
}
