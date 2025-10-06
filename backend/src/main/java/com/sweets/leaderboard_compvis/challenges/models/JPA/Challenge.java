package com.sweets.leaderboard_compvis.challenges.models.JPA;

import com.sweets.leaderboard_compvis.auditing.models.JPA.AuditedJpa;
import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "challenges")
public class Challenge extends AuditedJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    private OffsetDateTime startDate;

    @Column
    private OffsetDateTime endDate;

    @Column
    private String title;

    @Column
    private String description;

    @Column(nullable = false)
    private boolean isActive;

    public Challenge() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(OffsetDateTime endDate) {
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}