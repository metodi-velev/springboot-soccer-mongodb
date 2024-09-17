package com.soccer.mongo.dtos;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorResponseDto {
    private int httpStatusCode;
    private HttpStatus httpStatus;
    private String reason;
    private String message;
}
