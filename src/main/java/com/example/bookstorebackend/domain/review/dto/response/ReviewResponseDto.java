package com.example.bookstorebackend.domain.review.dto.response;

import com.example.bookstorebackend.domain.review.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(name = "ReviewResponse", description = "리뷰 상세 응답 DTO")
public class ReviewResponseDto {

    @Schema(description = "작성자 ID", example = "1")
    private final Long userId;

    @Schema(description = "도서 ID", example = "10")
    private final Long bookId;

    @Schema(description = "리뷰 내용", example = "내용이 알차고 추천할 만합니다.")
    private final String comment;

    @Schema(description = "별점 (1~5)", example = "4")
    private final Integer rating;

    @Schema(description = "작성 시각", example = "2025-09-14T12:34:56")
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
