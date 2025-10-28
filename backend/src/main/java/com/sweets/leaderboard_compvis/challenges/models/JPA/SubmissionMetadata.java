package com.sweets.leaderboard_compvis.challenges.models.JPA;

import jakarta.persistence.*;

@Entity
@Table(name = "submission_metadata")
public class SubmissionMetadata extends FileMetadata {

    @Column
    @JoinTable(name = "challenges", joinColumns = @JoinColumn(name = "challenge_id"))
    private long submissionId;
}
