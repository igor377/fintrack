package com.fintrack.demo.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fintrack.demo.user.entity.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    
    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}
