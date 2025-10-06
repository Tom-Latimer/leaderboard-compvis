package com.sweets.leaderboard_compvis.security.services;

import io.jsonwebtoken.JwtException;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String getUsernameFromToken(String token) throws JwtException;

    String generateToken(UserDetails userDetails);
}
