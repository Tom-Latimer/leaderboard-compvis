package com.sweets.leaderboard_compvis.challenges.models.DTO;

import java.util.UUID;

public class DatasetDownloadDto {

    private UUID attachmentId;
    private String fileName;
    private long contentLength;

    public DatasetDownloadDto() {
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

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }
}
