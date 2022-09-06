package sparta.seed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sparta.seed.util.TimeCustom;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
public class SeedApplication {

  public static void main(String[] args) {
    SpringApplication.run(SeedApplication.class, args);
  }

}
