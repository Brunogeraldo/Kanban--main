package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.User; // Certifique-se de que o caminho do pacote está correto

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}