package com.sweets.leaderboard_compvis.challenges.exceptions;

import com.sweets.leaderboard_compvis.infrastructure.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class SubmissionAlreadyExistsException extends BaseException {

    public SubmissionAlreadyExistsException(String message, ESubmissionErrorCodes errorCode) {
        super(message, HttpStatus.CONFLICT, errorCode.getErrorCode());
    }
}
