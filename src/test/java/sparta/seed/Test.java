package sparta.seed;

import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class Test {

    public static void main(String[] args) {


        LocalDateTime now = LocalDateTime.now();
        LocalDateTime currentdatetime = LocalDateTime.of(now.getYear(), now.getMonth(),now.getDayOfMonth(),now.getHour(), now.getMinute());
        System.out.println(now);
        System.out.println(currentdatetime);
        if(now.getHour()>4){
            System.out.println(LocalDate.from(now));
        } else{
            now = now.minusDays(1);
            System.out.println(LocalDate.from(now));
        }
    }
}
