package com.sweets.leaderboard_compvis.challenges.models.JPA;

import jakarta.persistence.*;

@Entity
@Table(name = "submission_team_members")
public class SubmissionTeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column
    private String email;

    @Column(nullable = false)
    private Boolean isContact;

    @ManyToOne
    @JoinColumn(name = "submission_id", referencedColumnName = "id")
    private SubmissionMetadata submissionMetadata;

    public SubmissionTeamMember() {
    }

    public SubmissionTeamMember(Long id, String firstName, String lastName, String email, Boolean isContact,
                                SubmissionMetadata submissionMetadata) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isContact = isContact;
        this.submissionMetadata = submissionMetadata;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean getIsContact() {
        return isContact;
    }

    public void setIsContact(boolean contact) {
        isContact = contact;
    }

    public SubmissionMetadata getSubmissionMetadata() {
        return submissionMetadata;
    }

    public void setSubmissionMetadata(SubmissionMetadata submissionMetadata) {
        this.submissionMetadata = submissionMetadata;
    }
}
