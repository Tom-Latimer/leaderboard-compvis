package com.sweets.leaderboard_compvis.users.models.DTO;

import com.sweets.leaderboard_compvis.users.models.EUserRole;

import java.util.Set;

public class CreateUserDto {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Set<EUserRole> roles;

    public CreateUserDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<EUserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<EUserRole> roles) {
        this.roles = roles;
    }
}
