package org.madrascheck.block_extension.exception.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode400 implements ErrorCode {

    PATH_PARAMETER_BAD_REQUEST("잘못된 경로 파라미터입니다."),
    DUPLICATE_EXTENSION("이미 동일한 확장자가 존재합니다."),
    MAX_CAPACITY_REACHED("확장자는 최대 200개까지만 추가할 수 있습니다."),
    ILLEGAL_INPUT_ARG("유효하지 않은 입력 값입니다."),
    ;

    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    private final String message;

    ErrorCode400(String message) {
        this.message = message;
    }
}
