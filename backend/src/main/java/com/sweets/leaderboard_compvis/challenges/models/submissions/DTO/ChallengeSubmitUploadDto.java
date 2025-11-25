package com.sweets.leaderboard_compvis.challenges.models.submissions.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ChallengeSubmitUploadDto {

    @NotBlank
    @Size(max = 50, message = "Team name cannot be longer than 50 characters.")
    private String teamName;

    @NotBlank
    @Size(max = 50, message = "Organization cannot be longer than 50 characters.")
    private String organization;

    @Valid
    @Size(min = 1, message = "Submission must have at least one team member.")
    private List<SubmissionTeamMemberDto> teamMembers;

    public ChallengeSubmitUploadDto() {

    }

    public ChallengeSubmitUploadDto(String teamName, String organization, List<SubmissionTeamMemberDto> teamMembers) {
        this.teamName = teamName;
        this.organization = organization;
        this.teamMembers = teamMembers;
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

    public List<SubmissionTeamMemberDto> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<SubmissionTeamMemberDto> teamMembers) {
        this.teamMembers = teamMembers;
    }
}
