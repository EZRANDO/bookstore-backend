package com.example.bookstorebackend.domain.book.dto.response;

import com.example.bookstorebackend.domain.book.entity.Book;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(name = "BookDetailResponse", description = "도서 상세 응답 DTO")
public class BookDetailResponseDto {

    @Schema(description = "도서 ID", example = "1")
    private final Long bookId;

    @Schema(description = "도서 제목", example = "이펙티브 자바")
    private final String title;

    @Schema(description = "저자명", example = "조슈아 블로크")
    private final String author;

    @Schema(description = "출판사명", example = "인사이트")
    private final String publisher;

    @Schema(description = "책 요약", example = "자바 개발자라면 반드시 읽어야 할 필독서")
    private final String summary;

    @Schema(description = "ISBN 번호", example = "9788966262281")
    private final String isbn;

    @Schema(description = "가격", example = "30000")
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
