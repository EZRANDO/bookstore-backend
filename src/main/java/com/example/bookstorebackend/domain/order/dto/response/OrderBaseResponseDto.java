package com.example.bookstorebackend.domain.order.dto.response;

import com.example.bookstorebackend.domain.order.entity.Order;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderBaseResponseDto {

    private final Long orderId;

    public static OrderBaseResponseDto from(Order order) {
        return OrderBaseResponseDto.builder()
                .orderId(order.getId())
                .build();
    }
}
