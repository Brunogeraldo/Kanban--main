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
                                .antMatchers("/login", "/register").permitAll() // Permitir acesso ao endpoint de login e registro
                                .anyRequest().authenticated() // Qualquer outra requisição precisa estar autenticada
                )
                .csrf(csrf -> csrf.disable()); // Desabilitar CSRF se não for necessário

        return http.build();
    }
}