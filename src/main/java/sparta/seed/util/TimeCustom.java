package sparta.seed.util;

import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Component;
import sparta.seed.todo.domain.Todo;
import sparta.seed.todo.dto.FirstWeekResponseDto;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;
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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        String formatedNow = formatter.format(now);
//        String time = formatedNow.split("\\s+")[1];
//        System.out.println(time);
        Calendar calendar = Calendar.getInstance(); //Thu May 03 14:43:32 KST 2022
        if(calendar.getTime().getHours()>4){
            return LocalDate.parse(formatter.format(calendar.getTime()).split("\\s+")[0]);

        } else{
            calendar.add(Calendar.HOUR, -24);
            return LocalDate.parse(formatter.format(calendar.getTime()).split("\\s+")[0]);
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
