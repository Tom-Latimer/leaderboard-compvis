package com.sweets.leaderboard_compvis.infrastructure.exceptions;

public interface ErrorCode {

    /**
     * Returns a unique, machine-readable string identifier for response errors
     *
     * @return string error code
     */
    String getErrorCode();
}
