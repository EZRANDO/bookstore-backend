package com.example.bookstorebackend.domain.review.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequestDto {

    @NotNull(message = "별점은 필수입니다.")
    @Min(1) @Max(5)
    Integer rating;

    @NotBlank(message = "리뷰는 필수입니다.")
    @Size(max=1000)
    String comment;
}
