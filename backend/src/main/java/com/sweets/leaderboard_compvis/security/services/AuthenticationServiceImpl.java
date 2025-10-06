package com.sweets.leaderboard_compvis.security.services;

import com.sweets.leaderboard_compvis.security.factories.UsernamePasswordAuthenticationTokenFactory;
import com.sweets.leaderboard_compvis.security.models.DTO.JwtResponse;
import com.sweets.leaderboard_compvis.security.models.DTO.LoginRequest;
import com.sweets.leaderboard_compvis.security.models.DTO.SignUpRequest;
import com.sweets.leaderboard_compvis.security.models.JPA.UserPrincipal;
import com.sweets.leaderboard_compvis.users.models.DTO.CreateUserDto;
import com.sweets.leaderboard_compvis.users.models.DTO.UserDto;
import com.sweets.leaderboard_compvis.users.models.EUserRole;
import com.sweets.leaderboard_compvis.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationServiceImpl(UserService userService,
                                     JwtService jwtService,
                                     AuthenticationManager authenticationManager,
                                     PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto signUp(SignUpRequest signUpRequest) {
        CreateUserDto createUserDto = new CreateUserDto();

        createUserDto.setEmail(signUpRequest.getEmail());

        //encode password
        createUserDto.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        createUserDto.setFirstName(signUpRequest.getFirstName());
        createUserDto.setLastName(signUpRequest.getLastName());
        createUserDto.setRoles(Collections.singleton(EUserRole.USER));

        //create user
        return userService.createUser(createUserDto);
    }

    public JwtResponse signIn(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authToken =
                UsernamePasswordAuthenticationTokenFactory.create(loginRequest);

        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authToken);

        //get principal to generate token
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String jwt = jwtService.generateToken(userPrincipal);

        //get roles for frontend
        List<String> roles = userPrincipal.getAuthorities().stream().map(item -> item.getAuthority())
                .toList();

        return new JwtResponse(jwt, userPrincipal.getUsername(), roles);
    }
}
