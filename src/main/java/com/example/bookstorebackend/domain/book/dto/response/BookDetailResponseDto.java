package com.example.bookstorebackend.domain.book.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BookDetailResponseDto {

    private String title;

    private String author;

    private String publisher;

    private String summary;

    private String isbn;

    private Integer price;

}
