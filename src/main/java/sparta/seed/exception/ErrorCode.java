package sparta.seed.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode { //이렇게 해주는 방법도 있다. 현재 코드에는 적용되지않았다.

    //enum이기 때문에 열거형 변수가 필드보다 아래에 있으면 method로 인식되고 에러가 난다.
    //회원가입 + 로그인
    DUPLE_NICK(HttpStatus.BAD_REQUEST, "400", "중복된 닉네임 입니다.");

//    INDEX_NOT_FOUND("1001", "인덱스가 존재하지 않습니다."),
//    BOARD_NOT_FOUND("1002", "게시글을 찾을 수 없습니다."),


    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;

}

