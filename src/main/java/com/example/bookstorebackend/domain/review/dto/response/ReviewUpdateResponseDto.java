package com.example.bookstorebackend.domain.review.dto.response;

import com.example.bookstorebackend.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewUpdateResponseDto {

    private final Long reviewId;

    private final LocalDateTime updatedAt;

    public static ReviewUpdateResponseDto from(Review review) {
        return ReviewUpdateResponseDto.builder()
                .reviewId(review.getId())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}
