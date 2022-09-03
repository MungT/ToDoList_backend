package sparta.seed.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice // @ControllerAdvice + @RequestBody (RestController의 예외처리에 대해서 AOP를 적용하기 위해 사용)
public class RestApiExceptionHandler {


    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        ErrorCode code = e.getErrorCode();
        log.error("handleCustomException: {}", code);
        return ErrorResponse.of(code);
//                .status(e.getErrorCode().getHttpStatus().value())
//                .body(new ErrorResponse(e.getErrorCode()));
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleApiRequestException(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .errorMessage(e.getMessage())
                        .errorCode("400")
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .build()
                );
    }
}