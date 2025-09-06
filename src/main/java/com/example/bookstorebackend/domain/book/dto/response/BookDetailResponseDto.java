package com.example.bookstorebackend.domain.book.dto.response;

import com.example.bookstorebackend.domain.book.entity.Book;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BookDetailResponseDto {

    private final Long bookId;

    private final String title;

    private final String author;

    private final String publisher;

    private final String summary;

    private final String isbn;

    private final Integer price;

    public static BookDetailResponseDto from(Book book) {
        return BookDetailResponseDto.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .summary(book.getSummary())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .build();
    }
}
