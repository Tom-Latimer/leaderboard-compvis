package com.sweets.leaderboard_compvis.challenges.repositories;

import com.sweets.leaderboard_compvis.challenges.models.JPA.Challenge;
import com.sweets.leaderboard_compvis.challenges.models.JPA.DatasetMetadata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    @Query("""
                SELECT d FROM Challenge c
                JOIN c.datasets d
                WHERE c.id = :challengeId
            """)
    Page<DatasetMetadata> findDatasetsByChallengeId(
            @Param("challengeId") Long challengeId,
            Pageable pageable);
}