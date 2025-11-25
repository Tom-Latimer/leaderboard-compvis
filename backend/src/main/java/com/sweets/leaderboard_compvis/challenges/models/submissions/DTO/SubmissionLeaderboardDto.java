package com.sweets.leaderboard_compvis.challenges.models.submissions.DTO;

import java.util.UUID;

public class SubmissionLeaderboardDto {
    private UUID submissionId;
    private String organization;
    private String teamName;
    private Double maxPrecision;
    private Double maxRecall;
    private Double split;
    private Long rank;

    public SubmissionLeaderboardDto() {
    }

    public SubmissionLeaderboardDto(UUID submissionId, String organization, String teamName, Double maxPrecision,
                                    Double maxRecall, Double split, Long rank) {
        this.submissionId = submissionId;
        this.organization = organization;
        this.teamName = teamName;
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
