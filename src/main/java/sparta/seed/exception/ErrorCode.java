package sparta.seed.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode { //이렇게 해주는 방법도 있다. 현재 코드에는 적용되지않았다.

    //enum이기 때문에 열거형 변수가 필드보다 아래에 있으면 method로 인식되고 에러가 난다.

    //회원가입 + 로그인
    DUPLE_NICK(HttpStatus.BAD_REQUEST, "400", "중복된 닉네임 입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "404", "유저가 존재하지 않습니다."),

    //투두 관련
    PASSED_AVAILABLE_TIME(HttpStatus.BAD_REQUEST,"400" ,"수정 가능한 기한이 지났습니다."),
    NOT_WRITER(HttpStatus.FORBIDDEN, "403", "투두 작성자가 아닙니다."),
    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, "404", "투두가 존재하지 않습니다."),
    RECENT_TODO_NOT_FOUND(HttpStatus.NOT_FOUND, "404", "최근 투두가 존재하지 않습니다."),
    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND,"404" ,"이미지 파일을 등록해 주세요."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND,"404" ,"해당 카테고리가 존재하지 않습니다.");

    // 팔로우 관련
    MYSELF_NOT_SELECTED(HttpStatus.BAD_REQUEST,"400","자기자신을 팔로우 할 수 없습니다."),
    FOLLOWLIST_EMPTY(HttpStatus.NOT_FOUND,"404" ,"팔로우한 친구가 없습니다.");



    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;

}

