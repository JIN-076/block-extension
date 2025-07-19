package org.madrascheck.block_extension.exception.base;

import org.madrascheck.block_extension.exception.code.ErrorCode;

public class IllegalStateException extends CustomException {

    public IllegalStateException(ErrorCode errorCode) {
        super(errorCode);
    }
}
