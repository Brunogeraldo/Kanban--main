package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.AuthRequest; // Certifique-se de que o caminho do pacote está correto

public interface UserRepository extends JpaRepository<AuthRequest, Long> {
    AuthRequest findByUsername(String username);
}