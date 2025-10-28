package com.sweets.leaderboard_compvis.challenges.repositories;

import com.sweets.leaderboard_compvis.challenges.models.JPA.DatasetMetadata;
import org.springframework.stereotype.Repository;

@Repository
public interface DatasetMetadataRepository extends FileMetadataRepository<DatasetMetadata> {
}
