package com.example.bookstorebackend.domain.cart.entity;


import com.example.bookstorebackend.common.entity.BaseEntity;
import com.example.bookstorebackend.common.enums.ErrorCode;
import com.example.bookstorebackend.common.exception.CustomException;
import com.example.bookstorebackend.domain.book.entity.Book;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "cart_item", uniqueConstraints = @UniqueConstraint(name="uq_cart_item_cart_book",
        columnNames={"cart_id","book_id"}))
public class CartItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false)
    private int quantity;

    public static CartItem of(Cart cart, Book book, int quantity) {
        return CartItem.builder()
                .cart(cart)
                .book(book)
                .quantity(quantity)
                .build();
    }

    public void changeQuantity(int newQty) {
        if (newQty <= 0) {
            throw new CustomException(ErrorCode.INVALID_CART_QUANTITY);
        }
        this.quantity = newQty;
    }

    public void increaseQuantity(int delta) {
        if (delta <= 0) {
            throw new CustomException(ErrorCode.INVALID_CART_QUANTITY_DELTA);
        }
        this.quantity += delta;
    }
}
