package com.sweets.leaderboard_compvis.challenges.models.submissions.DTO;

import java.util.List;
import java.util.UUID;

public class SubmissionLeaderboardDetailsDto extends SubmissionLeaderboardDto {
    private List<SubmissionTeamMemberDto> teamMembers;

    public SubmissionLeaderboardDetailsDto() {
    }

    public SubmissionLeaderboardDetailsDto(UUID submissionId, String organization, String teamName,
                                           Double maxPrecision, Double maxRecall, Double split, Long rank,
                                           List<SubmissionTeamMemberDto> teamMembers) {
        super(submissionId, organization, teamName, maxPrecision, maxRecall, split, rank);
        this.teamMembers = teamMembers;
    }

    public List<SubmissionTeamMemberDto> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<SubmissionTeamMemberDto> teamMembers) {
        this.teamMembers = teamMembers;
    }
}
