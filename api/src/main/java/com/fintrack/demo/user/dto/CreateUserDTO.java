package com.fintrack.demo.user.dto;

import java.nio.charset.StandardCharsets;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

public record CreateUserDTO(
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be less than 100 characters")
    String name,
    @NotBlank(message = "CPF is required")
    @CPF (message = "CPF should be valid")
    String cpf,
    @NotBlank(message = "Email is required")
    @Email (message = "Email should be valid")
    @Size(max = 255, message = "Email must be less than 255 characters")
    String email,
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 72, message = "Password must be between 8 and 72 characters")
    @Pattern(regexp = ".*[a-z].*", message = "Password must contain a lowercase letter")
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain an uppercase letter")
    @Pattern(regexp = ".*[0-9].*", message = "Password must contain a number")
    @Pattern(regexp = ".*[^\\p{L}\\p{N}\\s].*", message = "Password must contain a symbol")
    String password
) {
    @AssertTrue(message = "Password is too long (accented characters and emojis take more space)")
    public boolean isPasswordWithinBcryptLimit() {
        return password == null
            || password.getBytes(StandardCharsets.UTF_8).length <= 72;
    }
}