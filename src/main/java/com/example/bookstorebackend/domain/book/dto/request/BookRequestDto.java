package com.example.bookstorebackend.domain.book.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(name = "BookRequest", description = "도서 등록 요청 DTO")
public class BookRequestDto {

    @NotBlank(message = "도서 제목은 필수 입력값입니다.")
    @Size(max = 100, message = "도서 제목은 100자 이내여야 합니다.")
    @Schema(description = "도서 제목", example = "이펙티브 자바", maxLength = 100)
    private String title;

    @NotBlank(message = "저자는 필수 입력값입니다.")
    @Size(max = 50, message = "저자명은 50자 이내여야 합니다.")
    @Schema(description = "저자명", example = "조슈아 블로크", maxLength = 50)
    private String author;

    @NotBlank(message = "출판사는 필수 입력값입니다.")
    @Size(max = 50, message = "출판사명은 50자 이내여야 합니다.")
    @Schema(description = "출판사명", example = "인사이트", maxLength = 50)
    private String publisher;

    @Size(max = 1000, message = "책 요약은 1000자 이내여야 합니다.")
    @Schema(description = "책 요약", example = "자바 개발자라면 반드시 읽어야 할 필독서", maxLength = 1000)
    private String summary;

    @NotBlank(message = "ISBN은 필수 입력값입니다.")
    @Schema(description = "ISBN 번호", example = "9788966262281")
    private String isbn;

    @NotNull(message = "가격은 필수 입력값입니다.")
    @Positive(message = "가격은 0보다 큰 값이어야 합니다.")
    @Schema(description = "도서 가격", example = "30000", minimum = "1")
    private Integer price;
}