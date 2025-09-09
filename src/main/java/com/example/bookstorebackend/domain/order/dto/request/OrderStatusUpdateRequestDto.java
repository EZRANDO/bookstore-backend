package com.example.bookstorebackend.domain.order.dto.request;

import com.example.bookstorebackend.domain.order.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusUpdateRequestDto {

    @NotNull
    private OrderStatus status;
}