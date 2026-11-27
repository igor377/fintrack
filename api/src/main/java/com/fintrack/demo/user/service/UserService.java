package com.fintrack.demo.user.service;

import org.springframework.stereotype.Service;

import com.fintrack.demo.user.dto.CreateUserDTO;
import com.fintrack.demo.user.dto.UserResponseDTO;
import com.fintrack.demo.user.repository.UserRepository;
import com.fintrack.demo.user.exception.CPFAlreadyExistsException;
import com.fintrack.demo.user.exception.EmailAlreadyExistsException;
import com.fintrack.demo.user.mapper.UserMapper;
import com.fintrack.demo.user.entity.User;

import com.fintrack.demo.shared.util.CpfUtil;
import com.fintrack.demo.shared.util.EmailUtil;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service 
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional 
    public UserResponseDTO registerUser(CreateUserDTO createUser) {
        assertAvailable(createUser);
        User user = UserMapper.toEntity(createUser, passwordEncoder.encode(createUser.password()));
        return UserMapper.toResponseDTO(userRepository.save(user));
    }

    private void assertAvailable(CreateUserDTO createUser) {
        if (userRepository.existsByCpf(CpfUtil.cleanCpf(createUser.cpf()))) {
            throw new CPFAlreadyExistsException(createUser.cpf());
        }
        if (userRepository.existsByEmail(EmailUtil.normalizeEmail(createUser.email()))) {
            throw new EmailAlreadyExistsException(createUser.email());
        }
    }

}
