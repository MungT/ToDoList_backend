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

    Date now = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String formatedNow = formatter.format(now);
    String time = formatedNow.split("\\s+")[1];
    System.out.println(time);
    Calendar calendar = Calendar.getInstance();
    if(calendar.getTime().getHours()>=5){
      System.out.println((Date) calendar.getTime());
      System.out.println(formatter.format((Date) calendar.getTime()));
    } else{
      calendar.add(Calendar.HOUR, -24);
      System.out.println(formatter.format((Date) calendar.getTime()));

    }
  }

}
