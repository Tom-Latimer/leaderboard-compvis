package com.sweets.leaderboard_compvis.challenges.repositories;

import com.sweets.leaderboard_compvis.challenges.models.JPA.SubmissionMetadata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionMetadataRepository extends FileMetadataRepository<SubmissionMetadata> {

    Page<SubmissionMetadata> findSubmissionsByChallengeId(Long challengeId, Pageable pageable);

    boolean existsByChallengeIdAndSubmitterEmail(Long challengeId, String submitterEmail);
}
