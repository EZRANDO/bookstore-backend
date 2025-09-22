package com.example.bookstorebackend.domain.book.entity;

import com.example.bookstorebackend.common.entity.BaseEntity;
import com.example.bookstorebackend.domain.book.dto.request.BookRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "books")
public class Book extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150, nullable = false)
    private String title;

    @Column(length = 150, nullable = false)
    private String author;

    @Column(length = 150, nullable = false)
    private String publisher;

    private String summary;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private LocalDate publicationDate;

    public static Book createFromBook(BookRequestDto createRequestDto) {
        return Book.builder()
                .title(createRequestDto.getTitle())
                .author(createRequestDto.getAuthor())
                .publisher(createRequestDto.getPublisher())
                .summary(createRequestDto.getSummary())
                .isbn(createRequestDto.getIsbn())
                .price(createRequestDto.getPrice())
                .publicationDate(createRequestDto.getPublicationDate())
                .build();

    }

    public void updateBook(BookRequestDto dto) {
        this.title = dto.getTitle();
        this.author = dto.getAuthor();
        this.publisher = dto.getPublisher();
        this.summary = dto.getSummary();
        this.isbn = dto.getIsbn();
        this.price = dto.getPrice();
        this.publicationDate = dto.getPublicationDate();
    }
}
