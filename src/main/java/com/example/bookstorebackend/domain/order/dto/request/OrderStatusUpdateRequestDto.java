package com.example.bookstorebackend.domain.order.dto.request;

import com.example.bookstorebackend.domain.order.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "OrderStatusUpdateRequest", description = "주문 상태 변경 요청 DTO")
public class OrderStatusUpdateRequestDto {

    @NotNull
    @Schema(
            description = "변경할 주문 상태",
            example = "SHIPPED"
    )
    private OrderStatus status;
}