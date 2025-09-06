package com.example.bookstorebackend.domain.cart.dto.response;


import com.example.bookstorebackend.domain.cart.entity.CartItem;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartBaseResponseDto {

    private final Long cartId;

    public static CartBaseResponseDto from(CartItem cartItem) {
        return CartBaseResponseDto.builder()
                .cartId(cartItem.getId())
                .build();
    }
}
