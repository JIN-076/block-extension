package org.madrascheck.block_extension.exception.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode404 implements ErrorCode {

    EXTENSION_NOT_FOUND("존재하지 않는 확장자입니다."),
    ;

    private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    private final String message;

    ErrorCode404(String message) {
        this.message = message;
    }

}
