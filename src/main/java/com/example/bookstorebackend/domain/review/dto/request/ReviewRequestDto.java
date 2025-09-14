package com.example.bookstorebackend.domain.review.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "ReviewRequest", description = "리뷰 작성 요청 DTO")
public class ReviewRequestDto {

    @NotNull(message = "별점은 필수입니다.")
    @Min(1) @Max(5)
    @Schema(description = "별점 (1~5 사이 값)", example = "5", minimum = "1", maximum = "5")
    Integer rating;

    @NotBlank(message = "리뷰는 필수입니다.")
    @Size(max=1000)
    @Schema(description = "리뷰 내용", example = "정말 유익한 책입니다!", maxLength = 1000)
    String comment;
}
