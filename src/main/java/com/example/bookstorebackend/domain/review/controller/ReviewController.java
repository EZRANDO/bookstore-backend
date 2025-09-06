package com.example.bookstorebackend.domain.review.controller;

import com.example.bookstorebackend.common.enums.SuccessCode;
import com.example.bookstorebackend.common.response.ApiResponse;
import com.example.bookstorebackend.domain.review.dto.request.ReviewRequestDto;
import com.example.bookstorebackend.domain.review.dto.response.ReviewBaseResponseDto;
import com.example.bookstorebackend.domain.review.dto.response.ReviewResponseDto;
import com.example.bookstorebackend.domain.review.dto.response.ReviewUpdateResponseDto;
import com.example.bookstorebackend.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/books/{bookId}/reviews")
    public ResponseEntity<ApiResponse<ReviewBaseResponseDto>> createReview(
            @AuthenticationPrincipal(expression = "userId") Long userId,
            @PathVariable Long bookId,
            @Valid @RequestBody ReviewRequestDto req
    ) {
        ReviewBaseResponseDto body = reviewService.createReview(req, userId, bookId);
        return ApiResponse.onSuccess(SuccessCode.CREATE_REVIEW_SUCCESS, body);
    }

    @GetMapping("/reviews/me")
    public ResponseEntity<ApiResponse<List<ReviewResponseDto>>> findMyReviews(
            @AuthenticationPrincipal(expression = "userId") Long userId
    ) {
        List<ReviewResponseDto> body = reviewService.findAllReviews(userId);
        return ApiResponse.onSuccess(SuccessCode.GET_REVIEWS_SUCCESS, body);
    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> getReviewById(
            @AuthenticationPrincipal(expression = "userId") Long userId,
            @PathVariable Long reviewId
    ) {
        ReviewResponseDto body = reviewService.getReviewById(userId, reviewId);
        return ApiResponse.onSuccess(SuccessCode.GET_REVIEW_SUCCESS, body);
    }

    @PatchMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewUpdateResponseDto>> updateReview(
            @AuthenticationPrincipal(expression = "userId") Long userId,
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewRequestDto req
    ) {
        ReviewUpdateResponseDto body = reviewService.updateReview(req, userId, reviewId);
        return ApiResponse.onSuccess(SuccessCode.UPDATE_REVIEW_SUCCESS, body);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<Void>> deleteReview(
            @AuthenticationPrincipal(expression = "userId") Long userId,
            @PathVariable Long reviewId
    ) {
        reviewService.deleteReview(userId, reviewId);
        return ApiResponse.onSuccess(SuccessCode.DELETE_REVIEW_SUCCESS, null);
    }
}
