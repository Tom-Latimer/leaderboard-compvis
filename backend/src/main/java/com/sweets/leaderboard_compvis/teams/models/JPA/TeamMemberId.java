package com.sweets.leaderboard_compvis.teams.models.JPA;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class TeamMemberId implements Serializable {

    @Column
    private Long teamId;

    @Column(nullable = false)
    private String email;
}
