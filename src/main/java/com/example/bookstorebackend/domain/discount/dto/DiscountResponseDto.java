package com.example.bookstorebackend.domain.discount.dto;

import com.example.bookstorebackend.domain.discount.entity.Discount;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class DiscountResponseDto {

    @Schema(description = "할인 ID", example = "1")
    private final Long discountId;

    @Schema(description = "등록일시", example = "2025-09-14T12:34:56")
    private final LocalDateTime createdAt;

    public static DiscountResponseDto from(Discount discount) {
        return DiscountResponseDto.builder()
                .discountId(discount.getId())
                .createdAt(discount.getCreatedAt())
                .build();
    }
}
