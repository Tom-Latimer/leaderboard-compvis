package com.sweets.leaderboard_compvis.challenges.models.JPA;

import com.sweets.leaderboard_compvis.challenges.models.EMimeTypes;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "dataset_metadata")
public class DatasetMetadata extends FileMetadata {
    public DatasetMetadata() {
    }

    public DatasetMetadata(UUID attachmentId, String storageKey, String fileName, EMimeTypes contentType,
                           long contentLength) {
        super(attachmentId, storageKey, fileName, contentType, contentLength);
    }
}
