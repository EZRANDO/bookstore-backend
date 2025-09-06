package com.example.bookstorebackend.domain.cart.dto.response;


import com.example.bookstorebackend.domain.cart.entity.CartItem;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartItemResponseDto {

    private final Long cartItemId;

    private final Long bookId;

    private final int quantity;

    public static CartItemResponseDto from(CartItem cartItem) {
        return CartItemResponseDto.builder()
                .cartItemId(cartItem.getCart().getId())
                .bookId(cartItem.getBook().getId())
                .quantity(cartItem.getQuantity())
                .build();
    }
}
