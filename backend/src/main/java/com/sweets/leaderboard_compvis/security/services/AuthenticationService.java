package com.sweets.leaderboard_compvis.security.services;

import com.sweets.leaderboard_compvis.security.models.DTO.JwtResponse;
import com.sweets.leaderboard_compvis.security.models.DTO.LoginRequest;
import com.sweets.leaderboard_compvis.security.models.DTO.SignUpRequest;
import com.sweets.leaderboard_compvis.users.models.DTO.UserDto;

public interface AuthenticationService {
    UserDto signUp(SignUpRequest signUpRequest);

    JwtResponse signIn(LoginRequest loginRequest);
}
