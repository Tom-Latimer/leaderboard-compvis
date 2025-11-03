package com.sweets.leaderboard_compvis.challenges.models.submissions.DTO;

import java.util.UUID;

public class SubmissionLeaderboardDto {
    private UUID submissionId;
    private String SubmitterName;
    private String SubmitterEmail;

    public SubmissionLeaderboardDto() {
    }

    public SubmissionLeaderboardDto(UUID submissionId, String submitterName, String submitterEmail) {
        this.submissionId = submissionId;
        SubmitterName = submitterName;
        SubmitterEmail = submitterEmail;
    }

    public UUID getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(UUID submissionId) {
        this.submissionId = submissionId;
    }

    public String getSubmitterName() {
        return SubmitterName;
    }

    public void setSubmitterName(String submitterName) {
        SubmitterName = submitterName;
    }

    public String getSubmitterEmail() {
        return SubmitterEmail;
    }

    public void setSubmitterEmail(String submitterEmail) {
        SubmitterEmail = submitterEmail;
    }
}
