package com.example.bookstorebackend.domain.cart.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@Schema(name = "CartRequest", description = "장바구니 항목 추가/수정 요청 DTO")
public class CartRequestDto {

    @NotNull(message = "도서 ID는 필수입니다.")
    @Positive(message = "도서 ID는 양수여야 합니다.")
    @Schema(description = "도서 ID", example = "101")
    private Long bookId;

    @NotNull(message = "수량은 필수입니다.")
    @Min(value = 1, message = "수량은 최소 1 이상이어야 합니다.")
    @Schema(description = "수량", example = "3", minimum = "1")
    private int quantity;
}
