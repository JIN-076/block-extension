package org.madrascheck.block_extension.exception.base;

import org.madrascheck.block_extension.exception.code.ErrorCode;

public class DuplicateKeyException extends CustomException {

    public DuplicateKeyException(ErrorCode errorCode) {
        super(errorCode);
    }
}
