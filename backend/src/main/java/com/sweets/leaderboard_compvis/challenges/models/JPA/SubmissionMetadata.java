package com.sweets.leaderboard_compvis.challenges.models.JPA;

import com.sweets.leaderboard_compvis.challenges.models.EMimeTypes;
import com.sweets.leaderboard_compvis.challenges.models.ESubmissionStatus;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "submission_metadata")
public class SubmissionMetadata extends FileMetadata {

    @ManyToOne
    @JoinColumn(name = "challenge_id", referencedColumnName = "id")
    private Challenge challenge;

    @Column(nullable = false)
    private String submitterFirstName;

    @Column(nullable = false)
    private String submitterLastName;

    @Column(nullable = false)
    private String submitterEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ESubmissionStatus submissionStatus;

    public SubmissionMetadata() {
    }

    public SubmissionMetadata(UUID attachmentId, String storageKey, String fileName, EMimeTypes contentType,
                              long contentLength, Challenge challenge, String submitterFirstName,
                              String submitterLastName, String submitterEmail,
                              ESubmissionStatus submissionStatus) {
        super(attachmentId, storageKey, fileName, contentType, contentLength);
        this.challenge = challenge;
        this.submitterFirstName = submitterFirstName;
        this.submitterLastName = submitterLastName;
        this.submitterEmail = submitterEmail;
        this.submissionStatus = submissionStatus;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public String getSubmitterFirstName() {
        return submitterFirstName;
    }

    public void setSubmitterFirstName(String submitterName) {
        submitterName = submitterName;
    }

    public String getSubmitterLastName() {
        return submitterLastName;
    }

    public void setSubmitterLastName(String submitterLastName) {
        this.submitterLastName = submitterLastName;
    }

    public String getSubmitterEmail() {
        return submitterEmail;
    }

    public void setSubmitterEmail(String submitterEmail) {
        submitterEmail = submitterEmail;
    }

    public ESubmissionStatus getSubmissionStatus() {
        return submissionStatus;
    }

    public void setSubmissionStatus(ESubmissionStatus submissionStatus) {
        this.submissionStatus = submissionStatus;
    }
}
