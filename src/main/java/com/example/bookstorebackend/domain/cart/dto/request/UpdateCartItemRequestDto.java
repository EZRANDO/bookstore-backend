package com.example.bookstorebackend.domain.cart.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UpdateCartItemRequestDto {

    @NotNull(message = "도서 ID는 필수입니다.")
    private Long bookId;

    @NotNull
    @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
    private Integer quantity;
}

