package com.sweets.leaderboard_compvis.challenges.exceptions;

import com.sweets.leaderboard_compvis.infrastructure.exceptions.BaseException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class SubmissionNotFoundException extends BaseException {
    public SubmissionNotFoundException(UUID submissionId) {
        super(String.format("Submission with ID %s not found", submissionId), HttpStatus.NOT_FOUND, "ERR_SUB_NF");
    }
}
