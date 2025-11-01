package com.sweets.leaderboard_compvis.challenges.exceptions;

import com.sweets.leaderboard_compvis.infrastructure.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class ChallengeNotFoundException extends BaseException {
    public ChallengeNotFoundException(Long challengeId) {
        super(String.format("Challenge with ID %d not found", challengeId), HttpStatus.NOT_FOUND);
    }
}
