package org.madrascheck.block_extension.exception.base;

import lombok.Getter;
import org.madrascheck.block_extension.exception.code.ErrorCode;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
