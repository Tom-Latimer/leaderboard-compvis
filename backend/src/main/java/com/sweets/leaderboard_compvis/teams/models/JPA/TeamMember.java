package com.sweets.leaderboard_compvis.teams.models.JPA;

import jakarta.persistence.*;

@Entity
@Table(name = "team_members")
public class TeamMember {

    @EmbeddedId
    private TeamMemberId id;

    @ManyToOne
    @MapsId("teamId")
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @Column
    private String firstName;

    @Column
    private String lastName;
}
