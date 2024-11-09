package com.example.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    // Chave secreta para assinar o token
    private String secretKey = "your_secret_key"; // Troque por uma chave segura

    // Tempo de expiração do token (em milissegundos)
    private long expirationTime = 1000 * 60 * 60; // 1 hora

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Data de emissão
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime)) // Data de expiração
                .signWith(SignatureAlgorithm.HS256, secretKey) // Algoritmo de assinatura
                .compact();
    }

    // Método para validar o token
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // Método para extrair o nome de usuário do token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Método para verificar se o token está expirado
    private Boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // Método para extrair todas as claims do token
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}