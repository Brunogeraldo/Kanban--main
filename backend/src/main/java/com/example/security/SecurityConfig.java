package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .dispatcherTypeMatchers(HttpMethod.valueOf("/login")).permitAll() // Permitir acesso ao endpoint de login
                                .anyRequest().authenticated() // Qualquer outra requisição precisa estar autenticada
                );

        // Se você não precisa de proteção CSRF, pode desabilitá-la
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}