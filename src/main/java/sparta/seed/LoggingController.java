package sparta.seed;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggingController {

    @GetMapping("/health")
    public String checkHealth() {
        return "healthy";
    }
}