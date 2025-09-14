package com.example.bookstorebackend.domain.order.dto.response;

import com.example.bookstorebackend.domain.order.entity.Order;
import com.example.bookstorebackend.domain.order.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(name = "OrderUpdateResponse", description = "주문 상태 변경 응답 DTO")
public class OrderUpdateResponseDto {

    @Schema(description = "주문 ID", example = "1001")
    private final Long orderId;

    @Schema(description = "변경된 주문 상태", example = "SHIPPED")
    private final OrderStatus status;

    @Schema(description = "업데이트 시각", example = "2025-09-14T13:45:12")
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
