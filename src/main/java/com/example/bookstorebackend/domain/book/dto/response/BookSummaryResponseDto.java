package com.example.bookstorebackend.domain.book.dto.response;

import com.example.bookstorebackend.domain.book.entity.Book;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(name = "BookSummaryResponse", description = "도서 요약 응답 DTO")
public class BookSummaryResponseDto {

    @Schema(description = "도서 ID", example = "1")
    private final long bookId;

    @Schema(description = "도서 제목", example = "이펙티브 자바")
    private final String title;

    @Schema(description = "저자명", example = "조슈아 블로크")
    private final String author;

    @Schema(description = "출판사명", example = "인사이트")
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
