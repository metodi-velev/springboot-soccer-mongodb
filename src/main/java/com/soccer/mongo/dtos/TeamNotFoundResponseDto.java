package com.soccer.mongo.dtos;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
public class TeamNotFoundResponseDto {
    private int httpStatusCode;
    private HttpStatus httpStatus;
    private String reason;
    private String message;
}
