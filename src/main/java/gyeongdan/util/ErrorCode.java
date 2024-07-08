package gyeongdan.util;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // User ErrorMessage
    LOGIN_FAILED("로그인에 실패하였습니다.", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND("유저를 찾을 수 없습니다.", HttpStatus.UNAUTHORIZED),

    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
    }
