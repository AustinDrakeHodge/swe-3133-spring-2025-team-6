package com.gearedup.finalproject.controllers;

import com.gearedup.finalproject.dtos.RegisterUserRequest;
import com.gearedup.finalproject.dtos.UserDto;
import com.gearedup.finalproject.entities.User;
import com.gearedup.finalproject.mappers.UserMapper;
import com.gearedup.finalproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> registerUser( @RequestBody RegisterUserRequest request,
                                          UriComponentsBuilder uriBuilder) {
        var user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        var userDto = userMapper.toDto(user);

        var uri = uriBuilder.path("/users/{id}")
                            .buildAndExpand(userDto.getId())
                            .toUri();

        return ResponseEntity.created(uri).body(userDto);
    }
}