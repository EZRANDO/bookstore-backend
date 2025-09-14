package com.example.bookstorebackend.domain.order.dto.response;

import com.example.bookstorebackend.domain.order.entity.OrderItem;
import com.example.bookstorebackend.domain.order.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(name = "OrderItemResponse", description = "주문 항목 응답 DTO")
public class OrderItemResponseDto {

    @Schema(description = "주문 항목 ID", example = "5001")
    private final Long orderItemId;

    @Schema(description = "도서 ID", example = "123")
    private final Long bookId;

    @Schema(description = "수량", example = "2")
    private final Integer quantity;

    @Schema(description = "총 금액", example = "30000")
    private final Integer totalAmount;

    @Schema(description = "주문 상태", example = "CREATED")
    private final OrderStatus status;

    //스냅샷을 위한 필드는 제외함.

    public static OrderItemResponseDto from(OrderItem orderItem) {
        return OrderItemResponseDto.builder()
                .orderItemId(orderItem.getId())
                .bookId(orderItem.getBook().getId())
                .quantity(orderItem.getQuantity())
                .totalAmount(orderItem.getOrder().getTotalAmount())
                .status(orderItem.getOrder().getStatus())
                .build();
    }
}
