package com.example.bookstorebackend.domain.review.dto.response;

import com.example.bookstorebackend.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewBaseResponseDto {

    private final Long reviewId;

    private final LocalDateTime createdAt;

    public static ReviewBaseResponseDto from(Review review) {
        return ReviewBaseResponseDto.builder()
                .reviewId(review.getId())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
