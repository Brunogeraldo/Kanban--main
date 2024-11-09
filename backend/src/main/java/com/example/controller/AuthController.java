package com.example.controller;

import com.example.model.AuthResponse;
import com.example.model.AuthRequest; // Certifique-se de que esse modelo existe
import com.example.model.User; // Certifique-se de que o modelo User existe
import com.example.repository.UserRepository;
import com.example.Util.JwtUtil; // Certifique-se de que o import está correto
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Adicionando o PasswordEncoder

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            String token = jwtUtil.generateToken(authentication.getName());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) { // Mudando AuthResponse para User
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Usuário já existe!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); // Codificando a senha
        userRepository.save(user);
        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }
}