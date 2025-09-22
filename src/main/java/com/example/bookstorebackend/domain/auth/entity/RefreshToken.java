package com.example.bookstorebackend.domain.auth.entity;

import com.example.bookstorebackend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

//되도록 간단히 구성
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, unique = true, length = 256)
    private String token;

    public void updateToken(String newToken) {
        this.token = newToken;
    }
}
