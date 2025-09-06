package com.example.bookstorebackend.domain.order.dto.response;

import com.example.bookstorebackend.domain.order.entity.Order;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrderBaseResponseDto {

    private final Long orderId;

    private final LocalDateTime createdAt;

    public static OrderBaseResponseDto from(Order order) {
        return OrderBaseResponseDto.builder()
                .orderId(order.getId())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
