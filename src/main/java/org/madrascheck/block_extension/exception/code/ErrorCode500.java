package org.madrascheck.block_extension.exception.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode500 implements ErrorCode {

    INTERNAL_SERVER_ERROR("서버에서 알 수 없는 오류가 발생했습니다. 잠시 후 다시 시도해주세요.")
    ;

    private final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    private final String message;

    ErrorCode500(String message) {
        this.message = message;
    }
}
