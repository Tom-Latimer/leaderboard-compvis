package com.sweets.leaderboard_compvis.challenges.exceptions;

import com.sweets.leaderboard_compvis.infrastructure.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class SubmissionAlreadyExistsException extends BaseException {

    public SubmissionAlreadyExistsException(Long challengeId, String email) {
        super(String.format("Submission for challenge %d with email %s already exists", challengeId, email),
                HttpStatus.CONFLICT);
    }
}
