package com.sweets.leaderboard_compvis.challenges.models.submissions.DTO;

import java.util.UUID;

public class SubmissionListItemDto {

    private Long challengeId;
    private String challengeName;
    private UUID submissionId;
    private String submitterFirstName;
    private String submitterLastName;
    private String submitterEmail;

    public SubmissionListItemDto() {
    }

    public SubmissionListItemDto(Long challengeId, String challengeName, UUID submissionId, String submitterFirstName
            , String submitterLastName, String submitterEmail) {
        this.challengeId = challengeId;
        this.challengeName = challengeName;
        this.submissionId = submissionId;
        this.submitterFirstName = submitterFirstName;
        this.submitterLastName = submitterLastName;
        this.submitterEmail = submitterEmail;
    }

    public Long getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Long challengeId) {
        this.challengeId = challengeId;
    }

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    public UUID getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(UUID submissionId) {
        this.submissionId = submissionId;
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

    public String getSubmitterEmail() {
        return submitterEmail;
    }

    public void setSubmitterEmail(String submitterEmail) {
        this.submitterEmail = submitterEmail;
    }
}
