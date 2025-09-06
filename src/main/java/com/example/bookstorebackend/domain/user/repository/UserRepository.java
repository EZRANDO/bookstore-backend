package com.example.bookstorebackend.domain.user.repository;

import com.example.bookstorebackend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByIdAndDeletedFalse(Long userId);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    
    Optional<User> findByIdAndDeletedAtIsNull(Long userId);

}