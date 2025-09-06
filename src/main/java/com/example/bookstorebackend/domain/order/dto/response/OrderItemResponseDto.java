package com.example.bookstorebackend.domain.order.dto.response;

import com.example.bookstorebackend.domain.order.entity.OrderItem;
import com.example.bookstorebackend.domain.order.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItemResponseDto {

    private final Long orderItemId;

    private final Long bookId;

    private final Integer quantity;

    private final Integer totalAmount;

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
