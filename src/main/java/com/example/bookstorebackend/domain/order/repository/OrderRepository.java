package com.example.bookstorebackend.domain.order.repository;

import com.example.bookstorebackend.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByIdAndUser_Id(Long id, Long userId);

}