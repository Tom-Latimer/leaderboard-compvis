package com.sweets.leaderboard_compvis.challenges.models.JPA;

import com.sweets.leaderboard_compvis.auditing.models.JPA.AuditedJpa;
import com.sweets.leaderboard_compvis.challenges.models.EMimeTypes;
import com.sweets.leaderboard_compvis.challenges.models.mapper.EMimeTypesConverter;
import jakarta.persistence.*;

import java.util.UUID;

@MappedSuperclass
public abstract class FileMetadata extends AuditedJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID attachmentId;

    @Column(nullable = false, unique = true)
    private String storageKey;

    @Column(nullable = false)
    @Convert(converter = EMimeTypesConverter.class)
    private EMimeTypes contentType;

    @Column(nullable = false)
    private long contentLength;

    @Column(nullable = false)
    private String fileName;

    public FileMetadata() {
    }

    public FileMetadata(UUID attachmentId, String storageKey, String fileName, EMimeTypes contentType,
                        long contentLength) {
        super();

        this.attachmentId = attachmentId;
        this.storageKey = storageKey;
        this.fileName = fileName;
        this.contentType = contentType;
        this.contentLength = contentLength;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(UUID attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getStorageKey() {
        return storageKey;
    }

    public void setStorageKey(String storageKey) {
        this.storageKey = storageKey;
    }

    public EMimeTypes getContentType() {
        return contentType;
    }

    public void setContentType(EMimeTypes contentType) {
        this.contentType = contentType;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
