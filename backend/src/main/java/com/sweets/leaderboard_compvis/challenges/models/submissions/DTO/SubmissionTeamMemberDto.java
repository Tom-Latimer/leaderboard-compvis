package com.sweets.leaderboard_compvis.challenges.models.submissions.DTO;

import com.sweets.leaderboard_compvis.challenges.models.submissions.DTO.validation.ContactMustHaveEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@ContactMustHaveEmail
public class SubmissionTeamMemberDto {

    @NotBlank(message = "First name is required.")
    @Size(max = 20, message = "First name cannot be longer than 20 characters.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(max = 50, message = "Last name cannot be longer than 50 characters.")
    private String lastName;

    @Email(message = "Email must be a valid format.")
    @Size(max = 50, message = "Email cannot be longer than 50 characters.")
    private String email;

    @NotNull(message = "You must specify if this member is the primary contact.")
    private Boolean isContact;

    private SubmissionTeamMemberDto() {
    }

    public SubmissionTeamMemberDto(String firstName, String lastName, String email, Boolean isContact) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isContact = isContact;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public Boolean getIsContact() {
        return isContact;
    }

    public void setIsContact(Boolean contact) {
        isContact = contact;
    }
}
