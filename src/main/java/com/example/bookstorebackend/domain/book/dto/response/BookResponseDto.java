package com.example.bookstorebackend.domain.book.dto.response;

import com.example.bookstorebackend.domain.book.entity.Book;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BookResponseDto {

    private final Long id;

    public static BookResponseDto from(Book book) {
        return BookResponseDto.builder()
                .id(book.getId())
                .build();
    }
}
