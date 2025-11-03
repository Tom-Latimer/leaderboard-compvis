package com.sweets.leaderboard_compvis.challenges.models.submissions.DTO;

import java.util.UUID;

public class SubmissionStatusDto {
    private UUID submissionId;
    private String submissionStatus;

    public SubmissionStatusDto() {
    }

    public SubmissionStatusDto(UUID submissionId, String submissionStatus) {
        this.submissionId = submissionId;
        this.submissionStatus = submissionStatus;
    }

    public UUID getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(UUID submissionId) {
        this.submissionId = submissionId;
    }

    public String getSubmissionStatus() {
        return submissionStatus;
    }

    public void setSubmissionStatus(String submissionStatus) {
        this.submissionStatus = submissionStatus;
    }
}
