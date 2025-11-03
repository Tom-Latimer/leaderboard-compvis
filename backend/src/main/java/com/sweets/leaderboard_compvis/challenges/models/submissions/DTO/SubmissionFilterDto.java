package com.sweets.leaderboard_compvis.challenges.models.submissions.DTO;

import com.sweets.leaderboard_compvis.challenges.models.ESubmissionStatus;

public class SubmissionFilterDto {
    private Long challengeId;
    private ESubmissionStatus status;
    private String submitterEmail;
    private String submitterName;

    public SubmissionFilterDto() {
    }

    public SubmissionFilterDto(Long challengeId, ESubmissionStatus status, String submitterEmail,
                               String submitterName) {
        this.challengeId = challengeId;
        this.status = status;
        this.submitterEmail = submitterEmail;
        this.submitterName = submitterName;
    }

    public Long getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Long challengeId) {
        this.challengeId = challengeId;
    }

    public ESubmissionStatus getStatus() {
        return status;
    }

    public void setStatus(ESubmissionStatus status) {
        this.status = status;
    }

    public String getSubmitterEmail() {
        return submitterEmail;
    }

    public void setSubmitterEmail(String submitterEmail) {
        this.submitterEmail = submitterEmail;
    }

    public String getSubmitterName() {
        return submitterName;
    }

    public void setSubmitterName(String submitterName) {
        this.submitterName = submitterName;
    }
}
