package com.example.bookstorebackend.domain.book.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import jakarta.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class BookRequestDto {

    @NotBlank(message = "도서 제목은 필수 입력값입니다.")
    @Size(max = 100, message = "도서 제목은 100자 이내여야 합니다.")
    private String title;

    @NotBlank(message = "저자는 필수 입력값입니다.")
    @Size(max = 50, message = "저자명은 50자 이내여야 합니다.")
    private String author;

    @NotBlank(message = "출판사는 필수 입력값입니다.")
    @Size(max = 50, message = "출판사명은 50자 이내여야 합니다.")
    private String publisher;

    @Size(max = 1000, message = "책 요약은 1000자 이내여야 합니다.")
    private String summary;

    @NotBlank(message = "ISBN은 필수 입력값입니다.")
    private String isbn;

    @NotNull(message = "가격은 필수 입력값입니다.")
    @Positive(message = "가격은 0보다 큰 값이어야 합니다.")
    private Integer price;
}
