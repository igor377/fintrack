package com.fintrack.demo.user.mapper;

import com.fintrack.demo.user.dto.CreateUserDTO;
import com.fintrack.demo.user.dto.UserResponseDTO;
import com.fintrack.demo.user.entity.User;

import com.fintrack.demo.shared.util.CpfUtil;
import com.fintrack.demo.shared.util.EmailUtil;

public class UserMapper {
    
    public static User toEntity(CreateUserDTO createUserDTO, String hashedPassword) {
        User user = new User();
        user.setName(createUserDTO.name());
        user.setCpf(CpfUtil.cleanCpf(createUserDTO.cpf()));
        user.setEmail(EmailUtil.normalizeEmail(createUserDTO.email()));
        user.setPassword(hashedPassword);
        return user;
    }

    public static UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
            user.getId(),
            user.getName(),
            user.getCpf(),
            user.getEmail(),
            user.getRole()
        );
    }
}
