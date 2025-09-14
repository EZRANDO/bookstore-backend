package com.example.bookstorebackend.domain.cart.dto.response;


import com.example.bookstorebackend.domain.cart.entity.CartItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(name = "CartBaseResponse", description = "장바구니 기본 응답 DTO")
public class CartBaseResponseDto {

    @Schema(description = "장바구니 ID", example = "2001")
    private final Long cartId;

    public static CartBaseResponseDto from(CartItem cartItem) {
        return CartBaseResponseDto.builder()
                .cartId(cartItem.getId())
                .build();
    }
}