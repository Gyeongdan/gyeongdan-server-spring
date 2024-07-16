package gyeongdan.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResponse<String>> handleCustomException(CustomException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode().getMessage(), ex.getMessage());
        log.error("CustomException: ", ex);
        CommonResponse<String> commonResponse = new CommonResponse<>(
            errorResponse.getMessage(),
            ex.getMessage(),
            false
        );
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(commonResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<String>> handleGenericException(Exception ex) {
        log.error("Exception: ", ex); // Logs the full stack trace
        ErrorResponse errorResponse = new ErrorResponse("Internal server error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new CommonResponse<>(errorResponse.getMessage(), ex.getMessage(), false));
    }
}
