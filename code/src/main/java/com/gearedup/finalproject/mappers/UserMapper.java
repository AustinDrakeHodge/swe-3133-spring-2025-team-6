package com.gearedup.finalproject.mappers;

import com.gearedup.finalproject.dtos.RegisterUserRequest;
import com.gearedup.finalproject.dtos.UserDto;
import com.gearedup.finalproject.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(RegisterUserRequest request) {
        return User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .isAdmin(false)
                .build();
    }

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .isAdmin(user.isAdmin())
                .build();
    }
}