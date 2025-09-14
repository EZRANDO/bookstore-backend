package com.example.bookstorebackend.domain.order.dto.response;

import com.example.bookstorebackend.domain.order.entity.Order;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(name = "OrderBaseResponse", description = "주문 기본 응답 DTO")
public class OrderBaseResponseDto {

    @Schema(description = "주문 ID", example = "1001")
    private final Long orderId;

    @Schema(description = "주문 생성 시각", example = "2025-09-14T12:34:56")
    private final LocalDateTime createdAt;

    public static OrderBaseResponseDto from(Order order) {
        return OrderBaseResponseDto.builder()
                .orderId(order.getId())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
