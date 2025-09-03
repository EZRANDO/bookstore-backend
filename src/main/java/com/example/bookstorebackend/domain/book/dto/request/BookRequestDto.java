package com.example.bookstorebackend.domain.book.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookRequestDto {

    private String title;

    private String author;

    private String publisher;

    private String summary;

    private String isbn;

    private Integer price;

}
