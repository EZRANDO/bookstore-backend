package com.example.bookstorebackend.domain.cart.repository;

import com.example.bookstorebackend.domain.book.entity.Book;
import com.example.bookstorebackend.domain.cart.entity.Cart;
import com.example.bookstorebackend.domain.cart.entity.CartItem;
import com.example.bookstorebackend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndBook(Cart cart, Book book);
    List<CartItem> findAllByCart(Cart cart);
    @Query("""
        select distinct ci
        from CartItem ci
        join fetch ci.cart c
        join fetch ci.book b
        where c.user = :user
    """)
    List<CartItem> findAllWithCartAndBookByUser(@Param("user") User user);

    void deleteByCart_Id(Long cartId);
}