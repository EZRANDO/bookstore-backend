package com.example.bookstorebackend.domain.book.dto.response;

import com.example.bookstorebackend.domain.book.entity.Book;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BookSummaryResponseDto {

    private String title;

    private String author;

    private String publisher;

    public static BookSummaryResponseDto from(Book book) {
        return BookSummaryResponseDto.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .build();
    }
}
