package com.sweets.leaderboard_compvis.infrastructure.exceptions;

import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException {
    private final HttpStatus status;

    public BaseException(String message, HttpStatus httpStatus) {
        super(message);
        this.status = httpStatus;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
