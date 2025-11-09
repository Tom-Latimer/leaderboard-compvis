package com.sweets.leaderboard_compvis.challenges.models.submissions.DTO;

import com.sweets.leaderboard_compvis.challenges.models.ESubmissionStatus;

public class SubmissionFilterDto {
    private Long challengeId;
    private ESubmissionStatus status;
    private String submitterEmail;
    private String submitterFirstName;
    private String submitterLastName;

    public SubmissionFilterDto() {
    }

    public SubmissionFilterDto(Long challengeId, ESubmissionStatus status, String submitterEmail,
                               String submitterFirstName, String submitterLastName) {
        this.challengeId = challengeId;
        this.status = status;
        this.submitterEmail = submitterEmail;
        this.submitterFirstName = submitterFirstName;
        this.submitterLastName = submitterLastName;
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

    public String getSubmitterFirstName() {
        return submitterFirstName;
    }

    public void setSubmitterFirstName(String submitterFirstName) {
        this.submitterFirstName = submitterFirstName;
    }

    public String getSubmitterLastName() {
        return submitterLastName;
    }

    public void setSubmitterLastName(String submitterLastName) {
        this.submitterLastName = submitterLastName;
    }
}
