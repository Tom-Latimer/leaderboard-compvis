package com.sweets.leaderboard_compvis.infrastructure.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "ERR_BAD_REQUEST");
    }
}
