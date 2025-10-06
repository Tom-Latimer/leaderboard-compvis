package com.sweets.leaderboard_compvis.users.models.builders;

import com.sweets.leaderboard_compvis.infrastructure.models.builders.Builder;
import com.sweets.leaderboard_compvis.users.models.JPA.User;
import com.sweets.leaderboard_compvis.users.models.JPA.UserRole;

import java.util.HashSet;
import java.util.Set;

public class UserBuilder implements Builder<User> {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Set<UserRole> roles;

    public UserBuilder() {
        this.email = null;
        this.password = null;
        this.firstName = null;
        this.lastName = null;
        this.roles = null;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder role(UserRole role) {
        this.roles = new HashSet<>();
        this.roles.add(role);
        return this;
    }

    public UserBuilder roles(Set<UserRole> roles) {
        this.roles = roles;
        return this;
    }

    public User build() {
        return new User(email, password, firstName, lastName, roles);
    }
}
