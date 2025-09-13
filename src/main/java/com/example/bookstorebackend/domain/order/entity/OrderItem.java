package com.example.bookstorebackend.domain.order.entity;

import com.example.bookstorebackend.common.entity.BaseEntity;
import com.example.bookstorebackend.common.enums.ErrorCode;
import com.example.bookstorebackend.common.exception.CustomException;
import com.example.bookstorebackend.domain.book.entity.Book;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
//같은 도서 중복 행 금지
@Table(name = "order_item", uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_order_item_order_book",
                        columnNames = {"order_id", "book_id"})},
        indexes = {
                @Index(name = "idx_order_item_order", columnList = "order_id"),
                @Index(name = "idx_order_item_book", columnList = "book_id")
        })
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private Integer quantity;

    public static OrderItem of(Order order, Book book, int quantity) {
        if (quantity <= 0) {
            throw new CustomException(ErrorCode.INVALID_ORDER_QUANTITY);
        }
        return OrderItem.builder()
                .order(order)
                .book(book)
                .quantity(quantity)
                .build();
    }
}