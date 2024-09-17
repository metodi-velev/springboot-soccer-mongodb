package com.soccer.mongo.exception;

import com.soccer.mongo.dtos.TeamNotFoundResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(TeamNotFoundException.class)
    public ResponseEntity<TeamNotFoundResponseDto> teamNotFoundException(TeamNotFoundException exception) {
        log.error(exception.getMessage());
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }

    private ResponseEntity<TeamNotFoundResponseDto> createHttpResponse(HttpStatusCode httpStatus, String message) {
        return new ResponseEntity<>(TeamNotFoundResponseDto.builder()
                .httpStatusCode(httpStatus.value())
                .httpStatus((HttpStatus) httpStatus)
                .reason(((HttpStatus) httpStatus).getReasonPhrase().toUpperCase())
                .message(message)
                .build(), httpStatus);
    }
}
