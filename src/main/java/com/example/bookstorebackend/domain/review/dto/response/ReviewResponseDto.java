package com.example.bookstorebackend.domain.review.dto.response;

import com.example.bookstorebackend.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewResponseDto {

    private final Long userId;

    private final Long bookId;

    private final String comment;

    private final Integer rating;

    private final LocalDateTime createdAt;

    public static ReviewResponseDto from(Review review) {
        return ReviewResponseDto.builder()
                .userId(review.getUser().getId())
                .bookId(review.getBook().getId())
                .comment(review.getComment())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
