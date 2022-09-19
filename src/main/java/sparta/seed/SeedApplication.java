package sparta.seed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import sparta.seed.login.service.MemberService;
import sparta.seed.util.SchoolList;

import java.io.IOException;


@SpringBootApplication
@EnableJpaAuditing
public class SeedApplication {
  public static void main(String[] args) throws IOException {
    SpringApplication.run(SeedApplication.class, args);

  }
}
