package com.sweets.leaderboard_compvis.challenges.models.submissions.DTO;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class SubmissionApproveDto {

    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "1.0")
    private Double maxPrecision;

    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "1.0")
    private Double maxRecall;

    @NotNull
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "1.0")
    private Double split;

    public SubmissionApproveDto() {
    }

    public SubmissionApproveDto(Double maxPrecision, Double maxRecall, Double split) {
        this.maxPrecision = maxPrecision;
        this.maxRecall = maxRecall;
        this.split = split;
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
}
