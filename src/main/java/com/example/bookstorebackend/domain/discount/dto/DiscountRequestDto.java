package com.example.bookstorebackend.domain.discount.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DiscountRequestDto {

    @Schema(description = "할인을 적용할 책 ID", example = "1")
    @NotNull(message = "책 ID는 필수입니다.")
    private Long bookId;

    @Schema(description = "할인율(%)", example = "15")
    @NotNull(message = "할인율은 필수입니다.")
    @Min(value = 1, message = "할인율은 최소 1% 이상이어야 합니다.")
    @Max(value = 100, message = "할인율은 최대 100% 이하여야 합니다.")
    private Double discountRate;

    @Schema(description = "할인 시작일", example = "2025-09-21T00:00:00")
    @NotNull(message = "시작일은 필수입니다.")
    private LocalDateTime startAt;

    @Schema(description = "할인 종료일", example = "2025-09-30T23:59:59")
    @NotNull(message = "종료일은 필수입니다.")
    private LocalDateTime endAt;

    @Schema(description = "할인 유효 여부", example = "true")
    private Boolean isValid = true;
}
