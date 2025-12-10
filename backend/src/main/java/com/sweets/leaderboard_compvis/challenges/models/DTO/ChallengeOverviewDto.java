package com.sweets.leaderboard_compvis.challenges.models.DTO;

public class ChallengeOverviewDto {

    private String description;

    public ChallengeOverviewDto() {
    }

    public ChallengeOverviewDto(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
