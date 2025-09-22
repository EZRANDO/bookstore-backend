package com.example.bookstorebackend.domain.review.dto.response;

import com.example.bookstorebackend.domain.review.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(name = "ReviewBaseResponse", description = "리뷰 기본 응답 DTO (등록 시)")
public class ReviewBaseResponseDto {

    @Schema(description = "리뷰 ID", example = "101")
    private final Long reviewId;

    @Schema(description = "리뷰 생성 시각", example = "2025-09-14T12:34:56")
    private final LocalDateTime createdAt;

    public static ReviewBaseResponseDto from(Review review) {
        return ReviewBaseResponseDto.builder()
                .reviewId(review.getId())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
