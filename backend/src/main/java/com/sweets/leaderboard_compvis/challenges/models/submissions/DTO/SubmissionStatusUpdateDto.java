package com.sweets.leaderboard_compvis.challenges.models.submissions.DTO;

import com.sweets.leaderboard_compvis.challenges.models.ESubmissionStatus;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class SubmissionStatusUpdateDto {

    @NotNull(message = "Status cannot be null")
    private ESubmissionStatus status;

    @DecimalMin(value = "0.0", message = "Max precision must be greater than or equal to 0")
    @DecimalMax(value = "1.0", message = "Max precision must be less than or equal to 1")
    private Double maxPrecision;

    @DecimalMin(value = "0.0", message = "Max recall must be greater than or equal to 0")
    @DecimalMax(value = "1.0", message = "Max recall must be less than or equal to 1")
    private Double maxRecall;

    @DecimalMin(value = "0.0", message = "Split must be greater than or equal to 0")
    @DecimalMax(value = "1.0", message = "Split must be less than or equal to 1")
    private Double split;

    public SubmissionStatusUpdateDto() {
    }

    public SubmissionStatusUpdateDto(ESubmissionStatus status, Double maxPrecision, Double maxRecall, Double split) {
        this.status = status;
        this.maxPrecision = maxPrecision;
        this.maxRecall = maxRecall;
        this.split = split;
    }

    public ESubmissionStatus getStatus() {
        return status;
    }

    public void setStatus(ESubmissionStatus status) {
        this.status = status;
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
