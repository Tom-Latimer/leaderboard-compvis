package com.sweets.leaderboard_compvis.security.controllers;

import com.sweets.leaderboard_compvis.infrastructure.models.DTO.MessageResponse;
import com.sweets.leaderboard_compvis.security.models.DTO.JwtResponse;
import com.sweets.leaderboard_compvis.security.models.DTO.LoginRequest;
import com.sweets.leaderboard_compvis.security.models.DTO.SignUpRequest;
import com.sweets.leaderboard_compvis.security.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpRequest signUpRequest) {

        authenticationService.signUp(signUpRequest);

        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.signIn(loginRequest));
    }
}
