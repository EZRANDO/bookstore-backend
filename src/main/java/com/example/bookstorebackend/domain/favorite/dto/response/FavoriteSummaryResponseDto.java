package com.example.bookstorebackend.domain.favorite.dto.response;

import com.example.bookstorebackend.domain.favorite.entity.Favorite;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FavoriteSummaryResponseDto {

    private final Long favoriteId;

    private final Long userId;

    private final Long bookId;


    public static FavoriteSummaryResponseDto from(Favorite favorite) {
        return FavoriteSummaryResponseDto.builder()
                .favoriteId(favorite.getId())
                .userId(favorite.getUser().getId())
                .bookId(favorite.getBook().getId())
                .build();
    }
}
