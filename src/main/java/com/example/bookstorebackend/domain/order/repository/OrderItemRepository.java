package com.example.bookstorebackend.domain.order.repository;

import com.example.bookstorebackend.domain.order.entity.OrderItem;
import com.example.bookstorebackend.domain.user.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("""
        select distinct oi
        from OrderItem oi
        join fetch oi.order o
        join fetch oi.book b
        where o.user = :user
    """)
    List<OrderItem> findAllWithOrderAndBookByUser(@Param("user") User user);
}
