package com.example.bookstorebackend.domain.order.dto.response;

import com.example.bookstorebackend.domain.order.entity.Order;
import com.example.bookstorebackend.domain.order.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrderUpdateResponseDto {

    private final Long orderId;

    private final OrderStatus status;

    private final LocalDateTime updatedAt;

    //스냅샷을 위한 필드는 제외함.
    public static OrderUpdateResponseDto from(Order order) {
        return OrderUpdateResponseDto.builder()
                .orderId(order.getId())
                .status(order.getStatus())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}
