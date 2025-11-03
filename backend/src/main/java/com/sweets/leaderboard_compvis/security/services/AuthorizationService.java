package com.sweets.leaderboard_compvis.security.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component("auth")
public class AuthorizationService {

    public boolean hasRole(String role) {
        Authentication auth = getAuthentication();

        if (auth == null) {
            return false;
        }

        return auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals(role));
    }

    public boolean hasAnyRole(String... roles) {
        Authentication auth = getAuthentication();

        if (auth == null) {
            return false;
        }

        Set<String> roleSet = new HashSet<>(Arrays.asList(roles));
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(roleSet::contains);
    }


    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
