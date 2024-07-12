package gyeongdan.util;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    LOGIN_TOKEN_INVALID("로그인 토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);

    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

}
