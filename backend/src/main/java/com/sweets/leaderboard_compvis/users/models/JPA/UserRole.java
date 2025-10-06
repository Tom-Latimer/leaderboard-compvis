package com.sweets.leaderboard_compvis.users.models.JPA;

import com.sweets.leaderboard_compvis.users.models.EUserRole;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private EUserRole name;

    public UserRole() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EUserRole getName() {
        return name;
    }

    public void setName(EUserRole roleName) {
        this.name = roleName;
    }
}
