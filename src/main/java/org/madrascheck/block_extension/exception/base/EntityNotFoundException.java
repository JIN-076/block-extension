package org.madrascheck.block_extension.exception.base;

import org.madrascheck.block_extension.exception.code.ErrorCode;

public class EntityNotFoundException extends CustomException {

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
