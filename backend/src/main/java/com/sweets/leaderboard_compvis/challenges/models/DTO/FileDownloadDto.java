package com.sweets.leaderboard_compvis.challenges.models.DTO;

import com.sweets.leaderboard_compvis.challenges.models.EMimeTypes;

import java.io.InputStream;

public class FileDownloadDto {
    private final InputStream inputStream;
    private final String fileName;
    private final EMimeTypes contentType;
    private final long contentLength;

    public FileDownloadDto(InputStream inputStream, String fileName, EMimeTypes contentType, long contentLength) {
        this.inputStream = inputStream;
        this.fileName = fileName;
        this.contentType = contentType;
        this.contentLength = contentLength;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public String getFileName() {
        return fileName;
    }

    public EMimeTypes getContentType() {
        return contentType;
    }

    public long getContentLength() {
        return contentLength;
    }
}
