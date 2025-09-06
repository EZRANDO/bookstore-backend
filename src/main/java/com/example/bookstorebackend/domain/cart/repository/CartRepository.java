package com.example.bookstorebackend.domain.cart.repository;

import com.example.bookstorebackend.domain.cart.entity.Cart;
import com.example.bookstorebackend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);

}
