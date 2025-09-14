package com.example.bookstorebackend.domain.favorite.controller;

import com.example.bookstorebackend.common.enums.SuccessCode;
import com.example.bookstorebackend.common.response.ApiResponse;
import com.example.bookstorebackend.domain.favorite.dto.response.FavoriteResponseDto;
import com.example.bookstorebackend.domain.favorite.dto.response.FavoriteSummaryResponseDto;
import com.example.bookstorebackend.domain.favorite.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
@Tag(name = "Favorites", description = "도서 좋아요(위시리스트) API")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{bookId}")
    @Operation(summary = "도서 좋아요 추가", description = "특정 도서를 좋아요(위시리스트)에 추가합니다.")
    public ResponseEntity<ApiResponse<FavoriteResponseDto>> create(
            @AuthenticationPrincipal(expression = "userId") Long userId,
            @PathVariable Long bookId
    ) {
        FavoriteResponseDto body = favoriteService.createFavorite(userId, bookId);
        return ApiResponse.onSuccess(SuccessCode.CREATE_FAVORITE_SUCCESS, body);
    }

    @GetMapping
    @Operation(summary = "좋아요 목록 조회", description = "현재 로그인한 사용자가 좋아요한 도서 목록을 조회합니다.")
    public ResponseEntity<ApiResponse<List<FavoriteSummaryResponseDto>>> list(
            @AuthenticationPrincipal(expression = "userId") Long userId
    ) {
        List<FavoriteSummaryResponseDto> body = favoriteService.getAllFavorites(userId);
        return ApiResponse.onSuccess(SuccessCode.READ_FAVORITES_SUCCESS, body);
    }

    @DeleteMapping("/{favoriteId}")
    @Operation(summary = "좋아요 취소", description = "좋아요(위시리스트)에서 특정 도서를 삭제합니다.")
    public ResponseEntity<ApiResponse<Void>> deleteFavorite(
            @AuthenticationPrincipal(expression = "userId") Long userId,
            @PathVariable Long favoriteId
    ) {
        favoriteService.deleteFavorite(userId, favoriteId);
        return ApiResponse.onSuccess(SuccessCode.DELETE_FAVORITE_SUCCESS, null);
    }
}
