package sparta.seed.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
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
}
