package com.example.bookstorebackend.domain.favorite.dto.response;

import com.example.bookstorebackend.domain.favorite.entity.Favorite;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(name = "FavoriteResponse", description = "좋아요 생성 응답 DTO")
public class FavoriteResponseDto {

    @Schema(description = "좋아요 ID", example = "101")
    private final Long favoriteId;

    public static FavoriteResponseDto from(Favorite favorite) {
        return FavoriteResponseDto.builder()
                .favoriteId(favorite.getId())
                .build();
    }
}