package com.sweets.leaderboard_compvis.challenges.models.submissions.DTO;

import com.sweets.leaderboard_compvis.challenges.models.ESubmissionStatus;

import java.util.UUID;

public class SubmissionDto {
    private UUID attachmentId;
    private String fileName;
    private String submitterFirstName;
    private String submitterLastName;
    private String submitterEmail;
    private ESubmissionStatus status;

    public SubmissionDto() {
    }

    public UUID getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(UUID attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public ESubmissionStatus getStatus() {
        return status;
    }

    public void setStatus(ESubmissionStatus status) {
        this.status = status;
    }
}
