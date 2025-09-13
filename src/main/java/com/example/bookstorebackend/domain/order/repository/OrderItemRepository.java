package com.example.bookstorebackend.domain.order.repository;

import com.example.bookstorebackend.domain.order.entity.Order;
import com.example.bookstorebackend.domain.order.entity.OrderItem;
import com.example.bookstorebackend.domain.order.enums.OrderStatus;
import com.example.bookstorebackend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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


    @Query("""
        select (count(oi) > 0)
        from OrderItem oi
        join oi.order o
        where o.user.id = :userId
          and oi.book.id = :bookId
          and o.status = :status
    """)
    boolean existsPurchased(@Param("userId") Long userId,
                            @Param("bookId") Long bookId,
                            @Param("status") OrderStatus status);

    List<OrderItem> findByOrder(Order order);
}
