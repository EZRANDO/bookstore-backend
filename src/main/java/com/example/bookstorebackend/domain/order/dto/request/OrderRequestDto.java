package com.example.bookstorebackend.domain.order.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
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
@Schema(name = "OrderRequest", description = "주문 생성 요청 DTO")
public class OrderRequestDto {

    @NotEmpty(message = "주문 항목은 1개 이상이어야 합니다.")
    @Valid
    @Schema(description = "주문 항목 리스트(1개 이상)", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<OrderItemRequestDto> items;

}
