package com.sweets.leaderboard_compvis.challenges.models.JPA;

import com.sweets.leaderboard_compvis.challenges.models.EMimeTypes;
import com.sweets.leaderboard_compvis.challenges.models.ESubmissionStatus;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "submission_metadata")
public class SubmissionMetadata extends FileMetadata {

    @ManyToOne
    @JoinColumn(name = "challenge_id", referencedColumnName = "id", nullable = false)
    private Challenge challenge;

    @Column(nullable = false)
    private String teamName;

    @Column(nullable = false)
    private String organization;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "submissionMetadata", cascade = CascadeType.ALL, orphanRemoval =
            true)
    private Set<SubmissionTeamMember> submissionTeamMembers = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ESubmissionStatus submissionStatus;

    @Column
    private Double maxPrecision;

    @Column
    private Double maxRecall;

    @Column
    private Double split;

    public SubmissionMetadata() {
    }

    public SubmissionMetadata(UUID attachmentId, String storageKey, String fileName, EMimeTypes contentType,
                              long contentLength, Challenge challenge, String teamName,
                              String organization, Set<SubmissionTeamMember> submissionTeamMembers,
                              ESubmissionStatus submissionStatus) {
        super(attachmentId, storageKey, fileName, contentType, contentLength);
        this.challenge = challenge;
        this.teamName = teamName;
        this.organization = organization;
        this.submissionTeamMembers = submissionTeamMembers;
        this.submissionStatus = submissionStatus;
    }

    public SubmissionMetadata(UUID attachmentId, String storageKey, String fileName, EMimeTypes contentType,
                              long contentLength, Challenge challenge, String teamName,
                              String organization, Set<SubmissionTeamMember> submissionTeamMembers,
                              ESubmissionStatus submissionStatus,
                              Double maxPrecision, Double maxRecall, Double split) {
        super(attachmentId, storageKey, fileName, contentType, contentLength);
        this.challenge = challenge;
        this.teamName = teamName;
        this.organization = organization;
        this.submissionTeamMembers = submissionTeamMembers;
        this.submissionStatus = submissionStatus;
        this.maxPrecision = maxPrecision;
        this.maxRecall = maxRecall;
        this.split = split;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Set<SubmissionTeamMember> getSubmissionTeamMembers() {
        return submissionTeamMembers;
    }

    public void setSubmissionTeamMembers(Set<SubmissionTeamMember> submissionTeamMembers) {
        this.submissionTeamMembers = submissionTeamMembers;
    }

    public ESubmissionStatus getSubmissionStatus() {
        return submissionStatus;
    }

    public void setSubmissionStatus(ESubmissionStatus submissionStatus) {
        this.submissionStatus = submissionStatus;
    }

    public Double getMaxPrecision() {
        return maxPrecision;
    }

    public void setMaxPrecision(Double maxPrecision) {
        this.maxPrecision = maxPrecision;
    }

    public Double getMaxRecall() {
        return maxRecall;
    }

    public void setMaxRecall(Double maxRecall) {
        this.maxRecall = maxRecall;
    }

    public Double getSplit() {
        return split;
    }

    public void setSplit(Double split) {
        this.split = split;
    }
}
