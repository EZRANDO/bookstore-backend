package com.example.bookstorebackend.domain.favorite.dto.response;

import com.example.bookstorebackend.domain.favorite.entity.Favorite;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(name = "FavoriteSummaryResponse", description = "좋아요 요약 응답 DTO")
public class FavoriteSummaryResponseDto {

    @Schema(description = "좋아요 ID", example = "101")
    private final Long favoriteId;

    @Schema(description = "사용자 ID", example = "1")
    private final Long userId;

    @Schema(description = "도서 ID", example = "200")
    private final Long bookId;

    public static FavoriteSummaryResponseDto from(Favorite favorite) {
        return FavoriteSummaryResponseDto.builder()
                .favoriteId(favorite.getId())
                .userId(favorite.getUser().getId())
                .bookId(favorite.getBook().getId())
                .build();
    }
}
