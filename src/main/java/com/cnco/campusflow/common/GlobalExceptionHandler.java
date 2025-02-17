package com.cnco.campusflow.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorRespDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorRespDto.builder()
                        .msg(ex.getMessage())
                        .errorCode("C003")
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorRespDto> handleGeneralException(Exception ex) {
        ErrorWriter errorWriter = new ErrorWriter();
        log.error(errorWriter.getPrintStackTrace(ex));
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorRespDto.builder()
                        .msg("An unexpected error occurred")
                        .errorCode("S001")
                        .build());
    }
}
