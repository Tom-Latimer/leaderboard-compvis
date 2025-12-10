package com.sweets.leaderboard_compvis.challenges.models.DTO;

import java.util.UUID;

public class DatasetDownloadDto {

    private UUID attachmentId;
    private String fileName;
    private long fileSize;

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

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}
