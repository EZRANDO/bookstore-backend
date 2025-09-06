package com.example.bookstorebackend.domain.book.dto.response;

import com.example.bookstorebackend.domain.book.entity.Book;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BookSummaryResponseDto {

    private final long bookId;

    private final String title;

    private final String author;

    private final String publisher;

    public static BookSummaryResponseDto from(Book book) {
        return BookSummaryResponseDto.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .build();
    }
}
