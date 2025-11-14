package com.example.bookstorebackend.domain.discount.dto;

//@Getter
//@Builder
//@AllArgsConstructor
//public class DiscountSummaryResponseDto {
//
//    @Schema(description = "할인 ID", example = "1")
//    private Long id;
//
//    @Schema(description = "도서 ID", example = "10")
//    private Long bookId;
//
//    @Schema(description = "할인율(%)", example = "15.0")
//    private Double discountRate;
//
//    @Schema(description = "할인 시작일", example = "2025-09-21T00:00:00")
//    private LocalDateTime startDate;
//
//    @Schema(description = "할인 종료일", example = "2025-09-30T23:59:59")
//    private LocalDateTime endDate;
//
//    @Schema(description = "할인 유효 여부", example = "true")
//    private Boolean isValid;
//
//    @Schema(description = "생성일시", example = "2025-09-20T12:00:00")
//    private LocalDateTime createdAt;
//
//    @Schema(description = "수정일시", example = "2025-09-20T12:30:00")
//    private LocalDateTime updatedAt;
//
//    public static DiscountSummaryResponseDto from(com.example.bookstorebackend.domain.discount.entity.Discount discount) {
//        return DiscountSummaryResponseDto.builder()
//                .id(discount.getId())
//                .bookId(discount.getBook().getId())
//                .discountRate(discount.getDiscountRate())
//                .startDate(discount.getStartDate())
//                .endDate(discount.getEndDate())
//                .isValid(discount.getIsValid())
//                .createdAt(discount.getCreatedAt())
//                .updatedAt(discount.getUpdatedAt())
//                .build();
//    }
//}