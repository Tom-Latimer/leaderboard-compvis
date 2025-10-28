package com.sweets.leaderboard_compvis.challenges.repositories;

import com.sweets.leaderboard_compvis.challenges.models.JPA.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface FileMetadataRepository<T extends FileMetadata> extends JpaRepository<T, Long> {
    Optional<T> findByAttachmentId(UUID attachmentId);
}
