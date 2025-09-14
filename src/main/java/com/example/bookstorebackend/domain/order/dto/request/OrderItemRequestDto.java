package com.example.bookstorebackend.domain.order.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "OrderItemRequest", description = "주문 항목 요청 DTO")
public class OrderItemRequestDto {

    @NotNull(message = "도서 ID는 필수입니다.")
    @Schema(description = "도서 ID", example = "123")
    private Long bookId;

    @NotNull(message = "수량은 필수입니다.")
    @Min(value = 1, message = "수량은 최소 1 이상이어야 합니다.")
    @Schema(description = "주문 수량(최소 1)", example = "2", minimum = "1")
    private Integer quantity;
}
