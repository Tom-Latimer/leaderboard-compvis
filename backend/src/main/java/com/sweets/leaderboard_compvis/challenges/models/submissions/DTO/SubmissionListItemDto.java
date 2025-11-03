package com.sweets.leaderboard_compvis.challenges.models.submissions.DTO;

import java.util.UUID;

public class SubmissionListItemDto {

    private Long challengeId;
    private String challengeName;
    private UUID submissionId;
    private String SubmitterName;
    private String SubmitterEmail;

    public SubmissionListItemDto() {
    }

    public SubmissionListItemDto(Long challengeId, String challengeName, UUID submissionId, String submitterName,
                                 String submitterEmail) {
        this.challengeId = challengeId;
        this.challengeName = challengeName;
        this.submissionId = submissionId;
        SubmitterName = submitterName;
        SubmitterEmail = submitterEmail;
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
