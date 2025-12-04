package com.sweets.leaderboard_compvis.challenges.models.submissions.DTO;

import com.sweets.leaderboard_compvis.challenges.models.ESubmissionStatus;

import java.util.UUID;

public class SubmissionFilterDto {
    private Long challengeId;
    private String challengeName;
    private UUID submissionId;
    private ESubmissionStatus status;
    private String organization;
    private String teamName;

    public SubmissionFilterDto() {
    }

    public SubmissionFilterDto(Long challengeId, String challengeName, UUID submissionId, ESubmissionStatus status,
                               String organization, String teamName) {
        this.challengeId = challengeId;
        this.challengeName = challengeName;
        this.submissionId = submissionId;
        this.status = status;
        this.organization = organization;
        this.teamName = teamName;
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

    public ESubmissionStatus getStatus() {
        return status;
    }

    public void setStatus(ESubmissionStatus status) {
        this.status = status;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
