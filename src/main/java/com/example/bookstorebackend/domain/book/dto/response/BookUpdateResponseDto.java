package com.example.bookstorebackend.domain.book.dto.response;

import com.example.bookstorebackend.domain.book.entity.Book;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class BookUpdateResponseDto {

    private final Long bookId;

    private final LocalDateTime updatedAt;

    public static BookUpdateResponseDto from(Book book) {
        return BookUpdateResponseDto.builder()
                .bookId(book.getId())
                .updatedAt(book.getUpdatedAt())
                .build();
    }
}
