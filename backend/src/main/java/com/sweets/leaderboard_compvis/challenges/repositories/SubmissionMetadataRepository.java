package com.sweets.leaderboard_compvis.challenges.repositories;

import com.sweets.leaderboard_compvis.challenges.models.JPA.SubmissionMetadata;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionMetadataRepository extends FileMetadataRepository<SubmissionMetadata> {
}
