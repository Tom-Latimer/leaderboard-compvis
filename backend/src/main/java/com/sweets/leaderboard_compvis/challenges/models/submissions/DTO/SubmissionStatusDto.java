package com.sweets.leaderboard_compvis.challenges.models.submissions.DTO;

import java.util.UUID;

public class SubmissionStatusDto {
    private UUID submissionId;
    private String status;

    public SubmissionStatusDto() {
    }

    public SubmissionStatusDto(UUID submissionId, String submissionStatus) {
        this.submissionId = submissionId;
        this.status = submissionStatus;
    }

    public UUID getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(UUID submissionId) {
        this.submissionId = submissionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
