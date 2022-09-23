package sparta.seed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;


@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
public class SeedApplication {
  public static void main(String[] args) throws IOException {
    SpringApplication.run(SeedApplication.class, args);
  }
}
