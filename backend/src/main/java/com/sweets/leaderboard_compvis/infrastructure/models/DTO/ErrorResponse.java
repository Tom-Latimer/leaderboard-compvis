package com.sweets.leaderboard_compvis.infrastructure.models.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private int status;
    private String errorCode;
    private Instant timestamp;

    private Map<String, String> validationErrors;

    public ErrorResponse() {
    }

    public ErrorResponse(int status, String errorCode, Instant timestamp) {
        this.status = status;
        this.errorCode = errorCode;
        this.timestamp = timestamp;
    }

    public ErrorResponse(int status, String errorCode, Instant timestamp,
                         Map<String, String> validationErrors) {
        this.status = status;
        this.errorCode = errorCode;
        this.timestamp = timestamp;
        this.validationErrors = validationErrors;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Map<String, String> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
