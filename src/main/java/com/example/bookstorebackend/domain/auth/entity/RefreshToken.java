package com.example.bookstorebackend.domain.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

//
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @Column(name = "refresh_key", nullable = false)
    private String key;

    @Column(nullable = false)
    private String token;

    public void updateToken(String newToken) {
        this.token = newToken;
    }
}
