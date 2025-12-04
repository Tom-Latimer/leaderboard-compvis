package com.sweets.leaderboard_compvis.challenges.models.submissions.DTO;

import com.sweets.leaderboard_compvis.challenges.models.ESubmissionStatus;

import java.util.List;
import java.util.UUID;

public class SubmissionDetailsDto {

    private UUID submissionId;
    private String organization;
    private String teamName;
    private ESubmissionStatus status;
    private Double maxPrecision;
    private Double maxRecall;
    private Double split;
    private String challengeName;
    private String fileName;
    private Long fileSize;
    private List<SubmissionTeamMemberDto> teamMembers;

    public SubmissionDetailsDto() {
    }

    public SubmissionDetailsDto(UUID submissionId, String organization, String teamName, ESubmissionStatus status,
                                Double maxPrecision, Double maxRecall, Double split, String challengeName,
                                String fileName, Long fileSize, List<SubmissionTeamMemberDto> teamMembers) {
        this.submissionId = submissionId;
        this.organization = organization;
        this.teamName = teamName;
        this.status = status;
        this.maxPrecision = maxPrecision;
        this.maxRecall = maxRecall;
        this.split = split;
        this.challengeName = challengeName;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.teamMembers = teamMembers;
    }

    public UUID getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(UUID submissionId) {
        this.submissionId = submissionId;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public ESubmissionStatus getStatus() {
        return status;
    }

    public void setStatus(ESubmissionStatus status) {
        this.status = status;
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

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public List<SubmissionTeamMemberDto> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<SubmissionTeamMemberDto> teamMembers) {
        this.teamMembers = teamMembers;
    }
}
