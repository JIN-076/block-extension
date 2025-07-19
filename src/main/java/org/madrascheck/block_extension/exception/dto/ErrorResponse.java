package org.madrascheck.block_extension.exception.dto;

import lombok.Builder;
import lombok.Getter;

import org.madrascheck.block_extension.exception.code.ErrorCode;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int statusCode;
    private final String message;

    public static ResponseEntity<ErrorResponse> from(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .statusCode(errorCode.getHttpStatus().value())
                        .message(errorCode.getMessage())
                        .build()
                );
    }

    public static ResponseEntity<ErrorResponse> ofWithErrorMessage(ErrorCode errorCode, String message) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .statusCode(errorCode.getHttpStatus().value())
                        .message(message)
                        .build()
                );
    }
}
