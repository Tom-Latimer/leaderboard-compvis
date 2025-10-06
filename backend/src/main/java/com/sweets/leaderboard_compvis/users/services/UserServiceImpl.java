package com.sweets.leaderboard_compvis.users.services;

import com.sweets.leaderboard_compvis.users.exceptions.EmailAlreadyInUseException;
import com.sweets.leaderboard_compvis.users.models.DTO.CreateUserDto;
import com.sweets.leaderboard_compvis.users.models.DTO.UserDto;
import com.sweets.leaderboard_compvis.users.models.EUserRole;
import com.sweets.leaderboard_compvis.users.models.JPA.User;
import com.sweets.leaderboard_compvis.users.models.JPA.UserRole;
import com.sweets.leaderboard_compvis.users.models.builders.UserBuilder;
import com.sweets.leaderboard_compvis.users.models.mapper.UserMapper;
import com.sweets.leaderboard_compvis.users.repositories.RoleRepository;
import com.sweets.leaderboard_compvis.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDto createUser(CreateUserDto createUserDto) throws EmailAlreadyInUseException {
        //check if email is in use already
        if (userRepository.existsByEmail(createUserDto.getEmail())) {
            throw new EmailAlreadyInUseException("Email already in use");
        }

        //create user
        User user = new UserBuilder()
                .firstName(createUserDto.getFirstName())
                .lastName(createUserDto.getLastName())
                .email(createUserDto.getEmail())
                .password(createUserDto.getPassword())
                .roles(getRoles(createUserDto.getRoles()))
                .build();

        return userMapper.toDto(userRepository.save(user));
    }

    private Set<UserRole> getRoles(Set<EUserRole> roles) {
        List<UserRole> rolesFromDb = roleRepository.findByNameIn(roles);

        return new HashSet<>(rolesFromDb);
    }
}
