package com.fintrack.demo.user.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fintrack.demo.user.service.UserService;
import com.fintrack.demo.user.dto.CreateUserDTO;
import com.fintrack.demo.user.dto.UserResponseDTO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        UserResponseDTO responseUserDTO = userService.registerUser(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUserDTO);
    }
}
