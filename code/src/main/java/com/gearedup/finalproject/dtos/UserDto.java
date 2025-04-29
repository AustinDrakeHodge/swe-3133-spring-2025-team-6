package com.gearedup.finalproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private Boolean isAdmin;
}