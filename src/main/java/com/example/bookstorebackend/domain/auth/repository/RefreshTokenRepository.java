package com.example.bookstorebackend.domain.auth.repository;

import com.example.bookstorebackend.domain.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    void deleteByKey(String key);
    Optional<RefreshToken> findByKey(String key);
}
