package com.example.bookstorebackend.domain.review.dto.response;

import com.example.bookstorebackend.domain.review.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(name = "ReviewUpdateResponse", description = "리뷰 수정 응답 DTO")
public class ReviewUpdateResponseDto {

    @Schema(description = "리뷰 ID", example = "101")
    private final Long reviewId;

    @Schema(description = "수정된 시각", example = "2025-09-14T15:45:12")
    private final LocalDateTime updatedAt;

    public static ReviewUpdateResponseDto from(Review review) {
        return ReviewUpdateResponseDto.builder()
                .reviewId(review.getId())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}
