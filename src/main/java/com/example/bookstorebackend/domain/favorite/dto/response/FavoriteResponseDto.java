package com.example.bookstorebackend.domain.favorite.dto.response;

import com.example.bookstorebackend.domain.favorite.entity.Favorite;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FavoriteResponseDto {

    private final Long favoriteId;

    public static FavoriteResponseDto from(Favorite favorite) {
        return FavoriteResponseDto.builder()
                .favoriteId(favorite.getId())
                .build();
    }
}