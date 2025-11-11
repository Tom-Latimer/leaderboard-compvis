package com.sweets.leaderboard_compvis.challenges.models.submissions.DTO;

import java.util.UUID;

public class SubmissionLeaderboardDto {
    private UUID submissionId;
    private String submitterFirstName;
    private String submitterLastName;
    private String submitterEmail;
    private Double maxPrecision;
    private Double maxRecall;
    private Double split;
    private Long rank;

    public SubmissionLeaderboardDto() {
    }

    public SubmissionLeaderboardDto(UUID submissionId, String submitterFirstName, String submitterLastName,
                                    String submitterEmail, Double maxPrecision, Double maxRecall, Double split,
                                    Long rank) {
        this.submissionId = submissionId;
        this.submitterFirstName = submitterFirstName;
        this.submitterLastName = submitterLastName;
        this.submitterEmail = submitterEmail;
        this.maxPrecision = maxPrecision;
        this.maxRecall = maxRecall;
        this.split = split;
        this.rank = rank;
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

    public Double getMaxPrecision() {
        return maxPrecision;
    }

    public void setMaxPrecision(Double maxPrecision) {
        this.maxPrecision = maxPrecision;
    }

    public Double getMaxRecall() {
        return maxRecall;
    }

    public void setMaxRecall(Double maxRecall) {
        this.maxRecall = maxRecall;
    }

    public Double getSplit() {
        return split;
    }

    public void setSplit(Double split) {
        this.split = split;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }
}
