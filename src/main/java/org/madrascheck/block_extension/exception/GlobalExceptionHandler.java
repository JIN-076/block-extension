package org.madrascheck.block_extension.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.madrascheck.block_extension.exception.base.CustomException;
import org.madrascheck.block_extension.exception.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.madrascheck.block_extension.exception.code.ErrorCode400.ILLEGAL_INPUT_ARG;
import static org.madrascheck.block_extension.exception.code.ErrorCode400.PATH_PARAMETER_BAD_REQUEST;
import static org.madrascheck.block_extension.exception.code.ErrorCode500.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {CustomException.class})
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e, HttpServletRequest request) {
        log.info("Custom Exception: {}, Path: {}", e.getMessage(), getRequestURLWithQuery(request));
        return ErrorResponse.from(e.getErrorCode());
    }

    @ExceptionHandler(value = {
            BindException.class,
            MethodArgumentNotValidException.class
    })
    protected ResponseEntity<ErrorResponse> validationException(BindException e, HttpServletRequest request) {
        log.info("Validation Exception: {}, Path: {}", e.getMessage(), getRequestURLWithQuery(request));
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());
            builder.append(" 입력된 값: [");
            builder.append(fieldError.getRejectedValue());
            builder.append("]");
            builder.append(", ");
        }
        log.info(builder.toString());

        return ErrorResponse.ofWithErrorMessage(ILLEGAL_INPUT_ARG, getDefaultMessageWithErr(e, builder));
    }

    @ExceptionHandler(value = {
            MissingPathVariableException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<ErrorResponse> missingPathVariableException(
            Exception e, HttpServletRequest request
    ) {
        log.info("Missing Path Variable Exception: {}, Path: {}", e.getMessage(), getRequestURLWithQuery(request));
        return ErrorResponse.from(PATH_PARAMETER_BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(
            Exception e, HttpServletRequest request
    ) {
        log.info("Exception: {}, Path: {}", e.getMessage(), getRequestURLWithQuery(request));
        return ErrorResponse.from(INTERNAL_SERVER_ERROR);
    }

    private String getRequestURLWithQuery(HttpServletRequest request) {
        StringBuilder url = new StringBuilder(request.getRequestURI());
        return request.getQueryString() != null ? url.append("?").append(request.getQueryString()).toString() : url.toString();
    }

    private String getDefaultMessageWithErr(BindException e, StringBuilder builder) {
        return e.getFieldError() != null ? e.getFieldError().getDefaultMessage() : builder.toString();
    }
}
