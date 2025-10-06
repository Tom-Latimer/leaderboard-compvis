package com.sweets.leaderboard_compvis.teams.models.JPA;

import com.sweets.leaderboard_compvis.auditing.models.JPA.AuditedJpa;
import com.sweets.leaderboard_compvis.users.models.JPA.User;
import jakarta.persistence.*;

@Entity
@Table(name = "teams")
public class Team extends AuditedJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "team_admin")
    private User teamAdmin;

    @Column
    private String name;

    @Column(nullable = false)
    private boolean isActive;

    public Team() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getTeamAdmin() {
        return teamAdmin;
    }

    public void setTeamAdmin(User teamAdmin) {
        this.teamAdmin = teamAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
