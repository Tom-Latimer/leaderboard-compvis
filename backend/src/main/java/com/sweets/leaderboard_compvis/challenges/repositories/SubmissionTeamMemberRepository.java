package com.sweets.leaderboard_compvis.challenges.repositories;

import com.sweets.leaderboard_compvis.challenges.models.JPA.SubmissionTeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionTeamMemberRepository extends JpaRepository<SubmissionTeamMember, Long> {
    @Query("SELECT COUNT(tm) > 0 FROM SubmissionTeamMember tm " +
            "WHERE tm.submissionMetadata.challenge.id = :challengeId " +
            "AND tm.email = :email " +
            "AND tm.isContact = true")
    boolean isContactEmailTakenForChallenge(@Param("challengeId") Long challengeId, @Param("email") String email);
}
