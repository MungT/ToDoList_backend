package sparta.seed.util;

import org.springframework.stereotype.Component;
import sparta.seed.todo.dto.FirstWeekResponseDto;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class TimeCustom {

    public String customTime() {
// 현재 날짜/시간
        Date now = new Date();

// 현재 날짜/시간 출력
        System.out.println(now); // Thu May 03 14:43:32 KST 2022

// 포맷팅 정의
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

// 포맷팅 적용
        String formatedNow = formatter.format(now);

// 포맷팅 현재 날짜/시간 출력
        System.out.println(formatedNow); // 2022년 05월 03일 14시 43분 32초
        return formatedNow;
    }

    public LocalDate currentDate(){

        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime currentdatetime = LocalDateTime.of(now.getYear(), now.getMonth(),now.getDayOfMonth(),now.getHour(), now.getMinute());
//        System.out.println(now);
//        System.out.println(currentdatetime);
        if(now.getHour()>4){
            return LocalDate.from(now);
        } else{
            now = now.minusDays(1);
            return LocalDate.from(now);
        }
    }

    public FirstWeekResponseDto getDayOfWeek(LocalDate firstDate){

//        LocalDate localLastDate = LocalDate.parse(lastDate);
        int dayOfWeekValue = firstDate.getDayOfWeek().getValue();
        LocalDate startDate = firstDate;
        LocalDate endDate = firstDate.plusDays(7-dayOfWeekValue);
        return new FirstWeekResponseDto(startDate, endDate);
    }

}
