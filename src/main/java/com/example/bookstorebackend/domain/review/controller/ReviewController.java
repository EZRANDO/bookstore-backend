package com.example.bookstorebackend.domain.review.controller;

import com.example.bookstorebackend.common.enums.SuccessCode;
import com.example.bookstorebackend.common.response.ApiResponse;
import com.example.bookstorebackend.domain.review.dto.request.ReviewRequestDto;
import com.example.bookstorebackend.domain.review.dto.response.ReviewBaseResponseDto;
import com.example.bookstorebackend.domain.review.dto.response.ReviewResponseDto;
import com.example.bookstorebackend.domain.review.dto.response.ReviewUpdateResponseDto;
import com.example.bookstorebackend.domain.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Reviews", description = "리뷰 API")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/books/{bookId}/reviews")
    @Operation(summary = "리뷰 작성", description = "사용자가 특정 도서에 대해 리뷰를 작성합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "생성 성공",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "리뷰 작성 성공 예시",
                                    value = """
                                    {
                                      "isSuccess": true,
                                      "message": "리뷰를 생성했습니다.",
                                      "payload": {
                                        "reviewId": 101
                                      }
                                    }
                                    """
                            )
                    }
            )
    )
    public ResponseEntity<ApiResponse<ReviewBaseResponseDto>> createReview(
            @AuthenticationPrincipal(expression = "userId") Long userId,
            @PathVariable Long bookId,
            @Valid @RequestBody ReviewRequestDto req
    ) {
        ReviewBaseResponseDto body = reviewService.createReview(req, userId, bookId);
        return ApiResponse.onSuccess(SuccessCode.CREATE_REVIEW_SUCCESS, body);
    }


    @GetMapping("/reviews/me")
    @Operation(summary = "내 리뷰 조회", description = "현재 로그인한 사용자가 작성한 리뷰 전체를 조회합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "내 리뷰 전체 조회 성공 예시",
                                    value = """
                                    {
                                      "isSuccess": true,
                                      "message": "리뷰 목록을 조회했습니다.",
                                      "payload": [
                                        {
                                          "reviewId": 101,
                                          "bookId": 10,
                                          "rating": 5,
                                          "content": "정말 유익한 책입니다!",
                                          "createdAt": "2025-03-10T11:20:00"
                                        },
                                        {
                                          "reviewId": 102,
                                          "bookId": 8,
                                          "rating": 4,
                                          "content": "잘 읽혔습니다.",
                                          "createdAt": "2025-03-08T09:15:00"
                                        }
                                      ]
                                    }
                                    """
                            )
                    }
            )
    )
    public ResponseEntity<ApiResponse<List<ReviewResponseDto>>> findMyReviews(
            @AuthenticationPrincipal(expression = "userId") Long userId
    ) {
        List<ReviewResponseDto> body = reviewService.findAllReviews(userId);
        return ApiResponse.onSuccess(SuccessCode.GET_REVIEWS_SUCCESS, body);
    }

    @GetMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 상세 조회", description = "리뷰 ID로 특정 리뷰를 조회합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "리뷰 상세 조회 성공 예시",
                                    value = """
                                    {
                                      "isSuccess": true,
                                      "message": "리뷰를 조회했습니다.",
                                      "payload": {
                                        "reviewId": 101,
                                        "bookId": 10,
                                        "rating": 5,
                                        "content": "정말 유익한 책입니다!",
                                        "createdAt": "2025-03-10T11:20:00"
                                      }
                                    }
                                    """
                            )
                    }
            )
    )
    public ResponseEntity<ApiResponse<ReviewResponseDto>> getReviewById(
            @AuthenticationPrincipal(expression = "userId") Long userId,
            @PathVariable Long reviewId
    ) {
        ReviewResponseDto body = reviewService.getReviewById(userId, reviewId);
        return ApiResponse.onSuccess(SuccessCode.GET_REVIEW_SUCCESS, body);
    }

    @PatchMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 수정", description = "리뷰 ID로 특정 리뷰 내용을 수정합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "수정 성공",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "리뷰 수정 성공 예시",
                                    value = """
                                    {
                                      "isSuccess": true,
                                      "message": "리뷰를 수정했습니다.",
                                      "payload": {
                                        "reviewId": 101
                                      }
                                    }
                                    """
                            )
                    }
            )
    )
    public ResponseEntity<ApiResponse<ReviewUpdateResponseDto>> updateReview(
            @AuthenticationPrincipal(expression = "userId") Long userId,
            @PathVariable Long reviewId,
            @Valid @RequestBody ReviewRequestDto req
    ) {
        ReviewUpdateResponseDto body = reviewService.updateReview(req, userId, reviewId);
        return ApiResponse.onSuccess(SuccessCode.UPDATE_REVIEW_SUCCESS, body);
    }

    @DeleteMapping("/reviews/{reviewId}")
    @Operation(summary = "리뷰 삭제", description = "리뷰 ID로 특정 리뷰를 삭제합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "204",
            description = "삭제 성공",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "리뷰 삭제 성공 예시",
                                    value = """
                                    {
                                    }
                                    """
                            )
                    }
            )
    )
    public ResponseEntity<ApiResponse<Void>> deleteReview(
            @AuthenticationPrincipal(expression = "userId") Long userId,
            @PathVariable Long reviewId
    ) {
        reviewService.deleteReview(userId, reviewId);
        return ApiResponse.onSuccess(SuccessCode.DELETE_REVIEW_SUCCESS, null);
    }
}