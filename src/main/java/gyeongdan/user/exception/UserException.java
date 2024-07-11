package gyeongdan.user.exception;

import gyeongdan.util.CustomException;
import gyeongdan.util.ErrorCode;
import lombok.Getter;

@Getter
public class UserException extends CustomException {

    public UserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
