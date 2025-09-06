package com.example.bookstorebackend.domain.cart.dto.request;

import com.example.bookstorebackend.domain.cart.entity.CartItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CartRequestDto {

    @NotNull(message = "도서 ID는 필수입니다.")
    @Positive(message = "도서 ID는 양수여야 합니다.")
    private Long bookId;

    @NotNull(message = "수량은 필수입니다.")
    @Min(value = 1, message = "수량은 최소 1 이상이어야 합니다.")
    private int quantity;

    public static CartRequestDto from(CartItem cartItem) {
        return CartRequestDto.builder()
                .bookId(cartItem.getBook().getId())
                .quantity(cartItem.getQuantity())
                .build();
    }
}
