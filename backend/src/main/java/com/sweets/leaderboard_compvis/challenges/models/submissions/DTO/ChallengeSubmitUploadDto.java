package com.sweets.leaderboard_compvis.challenges.models.submissions.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ChallengeSubmitUploadDto {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    public ChallengeSubmitUploadDto() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
