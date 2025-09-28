package com.sweets.leaderboard_compvis.teams.models.JPA;

import com.sweets.leaderboard_compvis.models.User;
import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "team_admin")
    private User teamAdmin;

    @Column
    private String name;

    @Column
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private boolean isActive;
}
