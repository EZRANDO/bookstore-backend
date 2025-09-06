package com.example.bookstorebackend.domain.order.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDto {

    @NotEmpty(message = "주문 항목은 1개 이상이어야 합니다.")
    private List<OrderItemRequestDto> items;

}
