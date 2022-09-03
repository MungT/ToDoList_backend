package sparta.seed.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// Entity가 자동으로 컬럼으로 인식합니다.
@MappedSuperclass    // Entity가 자동으로 컬럼으로 인식합니다. //Timestamped를 상속한 녀석이 자동으로 생성시간과 수정시간을 컬럼으로 잡도록 도와주는 녀석
@EntityListeners(AuditingEntityListener.class) // 생성/변경 시간을 변동시에 자동으로 업데이트합니다.
@Getter // 이걸 빠뜨리는 실수 많이 하는데 빠드리면 Timestamped를 못가져간다.
public abstract class Timestamped { //abstract클래스는 new Timestamped와 같이 생성할 수 없다. 상속으로만 사용가능 - 추상클래스는 상속 받아서 각자 구현해줘야하기 때문


    @CreatedDate //생성시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime createdAt; //LocalDateTime 시간을 나타내는 자료형

    @LastModifiedDate//수정시간
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;



}
