package com.sweets.leaderboard_compvis.challenges.models.submissions.DTO;

import com.sweets.leaderboard_compvis.challenges.models.ESubmissionStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public class SubmissionListItemDto {

    private Long challengeId;
    private String challengeName;
    private UUID submissionId;
    private ESubmissionStatus status;
    private OffsetDateTime submittedAt;
    private Long rank;

    public SubmissionListItemDto() {
    }

    public SubmissionListItemDto(Long challengeId, String challengeName, UUID submissionId, ESubmissionStatus status,
                                 OffsetDateTime submittedAt, Long rank) {
        this.challengeId = challengeId;
        this.challengeName = challengeName;
        this.submissionId = submissionId;
        this.status = status;
        this.submittedAt = submittedAt;
        this.rank = rank;
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

    public OffsetDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(OffsetDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }
}
