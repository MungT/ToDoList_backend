package sparta.seed.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

//
@Getter
@Builder
public class ErrorResponse extends Throwable {

    private String errorMessage;
    private String errorCode;
    private HttpStatus httpStatus;

    public static ResponseEntity<ErrorResponse> of(ErrorCode code) {
        return ResponseEntity
                .status(code.getHttpStatus())
                .body(
                        ErrorResponse.builder()
                                .errorMessage(code.getErrorMessage())
                                .errorCode(code.getErrorCode())
                                .httpStatus(code.getHttpStatus())
                                .build()
                );
    }
}
