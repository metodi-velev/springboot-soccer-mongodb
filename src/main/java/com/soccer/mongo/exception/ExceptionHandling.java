package com.soccer.mongo.exception;

import com.soccer.mongo.dtos.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(TeamNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleTeamNotFoundException(TeamNotFoundException exception) {
        log.error("Team not found: {}", exception.getMessage(), exception);
        return buildErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    private ResponseEntity<ErrorResponseDto> buildErrorResponse(HttpStatus httpStatus, String message) {
        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .httpStatusCode(httpStatus.value())
                .httpStatus(HttpStatus.valueOf(httpStatus.name()))
                .reason(httpStatus.getReasonPhrase())
                .message(message)
                .build();

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
