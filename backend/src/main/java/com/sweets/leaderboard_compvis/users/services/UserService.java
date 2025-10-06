package com.sweets.leaderboard_compvis.users.services;

import com.sweets.leaderboard_compvis.users.exceptions.EmailAlreadyInUseException;
import com.sweets.leaderboard_compvis.users.models.DTO.CreateUserDto;
import com.sweets.leaderboard_compvis.users.models.DTO.UserDto;
import com.sweets.leaderboard_compvis.users.models.JPA.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    User save(User user);

    @Transactional
    UserDto createUser(CreateUserDto createUserDto) throws EmailAlreadyInUseException;
}
