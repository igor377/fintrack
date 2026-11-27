package com.fintrack.demo.user.dto;

import java.util.UUID;

import com.fintrack.demo.user.entity.User;
import com.fintrack.demo.user.entity.UserType;

public record UserResponseDTO(
    UUID id,
    String name,
    String cpf,
    String email,
    UserType role
) {
}
