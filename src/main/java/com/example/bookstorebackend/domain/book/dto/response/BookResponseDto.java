package com.example.bookstorebackend.domain.book.dto.response;

import com.example.bookstorebackend.domain.book.entity.Book;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@Schema(name = "BookResponse", description = "도서 기본 응답 DTO")
public class BookResponseDto {

    @Schema(description = "도서 ID", example = "1")
    private final Long bookId;

    @Schema(description = "등록일시", example = "2025-09-14T12:34:56")
    private final LocalDateTime createdAt;

    public static BookResponseDto from(Book book) {
        return BookResponseDto.builder()
                .bookId(book.getId())
                .createdAt(book.getCreatedAt())
                .build();
    }
}
