package com.example.bookstorebackend.common.config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtKeyConfig {

    @Bean
    public SecretKey jwtSigningKey(@Value("${jwt.secret}") String base64Secret) {
        byte[] kb = Decoders.BASE64.decode(base64Secret.trim());
        if (kb.length < 32) {
            throw new IllegalStateException("JWT secret must be >= 256 bits (HS256).");
        }
        return Keys.hmacShaKeyFor(kb);
    }
}