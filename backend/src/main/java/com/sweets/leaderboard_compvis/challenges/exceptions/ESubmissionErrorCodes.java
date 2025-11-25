package com.sweets.leaderboard_compvis.challenges.exceptions;

import com.sweets.leaderboard_compvis.infrastructure.exceptions.ErrorCode;

public enum ESubmissionErrorCodes implements ErrorCode {
    TeamNameAlreadyExists("ERR_TEAM_NAME_AE"),
    ContactEmailAlreadyExists("ERR_CONTACT_EMAIL_AE"),
    ;

    private final String value;

    ESubmissionErrorCodes(String value) {
        this.value = value;
    }

    @Override
    public String getErrorCode() {
        return value;
    }
}
