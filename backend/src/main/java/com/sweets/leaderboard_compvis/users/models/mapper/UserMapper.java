package com.sweets.leaderboard_compvis.users.models.mapper;

import com.sweets.leaderboard_compvis.users.models.DTO.CreateUserDto;
import com.sweets.leaderboard_compvis.users.models.DTO.UserDto;
import com.sweets.leaderboard_compvis.users.models.JPA.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    User toEntity(CreateUserDto createUserDto);

    UserDto toDto(User user);
}
