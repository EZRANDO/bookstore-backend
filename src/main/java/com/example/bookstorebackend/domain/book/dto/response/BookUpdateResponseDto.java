package com.example.bookstorebackend.domain.book.dto.response;

import com.example.bookstorebackend.domain.book.entity.Book;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@Schema(name = "BookUpdateResponse", description = "도서 수정 응답 DTO")
public class BookUpdateResponseDto {

    @Schema(description = "도서 ID", example = "1")
    private final Long bookId;

    @Schema(description = "수정된 시각", example = "2025-09-14T15:42:00")
    private final LocalDateTime updatedAt;

    public static BookUpdateResponseDto from(Book book) {
        return BookUpdateResponseDto.builder()
                .bookId(book.getId())
                .updatedAt(book.getUpdatedAt())
                .build();
    }
}
