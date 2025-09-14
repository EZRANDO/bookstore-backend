package com.example.bookstorebackend.domain.cart.dto.response;


import com.example.bookstorebackend.domain.cart.entity.CartItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(name = "CartItemResponse", description = "장바구니 항목 응답 DTO")
public class CartItemResponseDto {

    @Schema(description = "장바구니 항목 ID", example = "3001")
    private final Long cartItemId;

    @Schema(description = "도서 ID", example = "101")
    private final Long bookId;

    @Schema(description = "수량", example = "2")
    private final int quantity;

    public static CartItemResponseDto from(CartItem cartItem) {
        return CartItemResponseDto.builder()
                .cartItemId(cartItem.getCart().getId())
                .bookId(cartItem.getBook().getId())
                .quantity(cartItem.getQuantity())
                .build();
    }
}
