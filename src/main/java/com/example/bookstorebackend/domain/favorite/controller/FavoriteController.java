package com.example.bookstorebackend.domain.favorite.controller;

import com.example.bookstorebackend.common.enums.SuccessCode;
import com.example.bookstorebackend.common.response.ApiResponse;
import com.example.bookstorebackend.domain.favorite.dto.response.FavoriteResponseDto;
import com.example.bookstorebackend.domain.favorite.dto.response.FavoriteSummaryResponseDto;
import com.example.bookstorebackend.domain.favorite.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{bookId}")
    public ResponseEntity<ApiResponse<FavoriteResponseDto>> create(
            @AuthenticationPrincipal(expression = "userId") Long userId,
            @PathVariable Long bookId
    ) {
        FavoriteResponseDto body = favoriteService.createFavorite(userId, bookId);
        return ApiResponse.onSuccess(SuccessCode.CREATE_FAVORITE_SUCCESS, body);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FavoriteSummaryResponseDto>>> list(
            @AuthenticationPrincipal(expression = "userId") Long userId
    ) {
        List<FavoriteSummaryResponseDto> body = favoriteService.getAllFavorites(userId);
        return ApiResponse.onSuccess(SuccessCode.READ_FAVORITES_SUCCESS, body);
    }

    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<ApiResponse<Void>> deleteFavorite(
            @AuthenticationPrincipal(expression = "userId") Long userId,
            @PathVariable Long favoriteId
    ) {
        favoriteService.deleteFavorite(userId, favoriteId);
        return ApiResponse.onSuccess(SuccessCode.DELETE_FAVORITE_SUCCESS, null);
    }
}

