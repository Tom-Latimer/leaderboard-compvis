package com.sweets.leaderboard_compvis.security.factories;

import com.sweets.leaderboard_compvis.security.models.DTO.LoginRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class UsernamePasswordAuthenticationTokenFactory {

    public static UsernamePasswordAuthenticationToken create(LoginRequest loginRequest) {
        return new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
    }
}
