package com.example.bookstorebackend.domain.book.controller;


import com.example.bookstorebackend.common.enums.SuccessCode;
import com.example.bookstorebackend.common.response.ApiResponse;
import com.example.bookstorebackend.domain.book.dto.request.BookRequestDto;
import com.example.bookstorebackend.domain.book.dto.response.BookDetailResponseDto;
import com.example.bookstorebackend.domain.book.dto.response.BookResponseDto;
import com.example.bookstorebackend.domain.book.dto.response.BookSummaryResponseDto;
import com.example.bookstorebackend.domain.book.dto.response.BookUpdateResponseDto;
import com.example.bookstorebackend.domain.book.service.BookService;
import com.example.bookstorebackend.security.principal.CustomUserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;

    @PostMapping("/books")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<BookResponseDto>> createBook(
            @Valid @RequestBody BookRequestDto request, @AuthenticationPrincipal CustomUserPrincipal principal
    ) {

        BookResponseDto responseDto = bookService.createBook(request, principal.getUserId());
        return ApiResponse.onSuccess(SuccessCode.CREATE_BOOK_SUCCESS, responseDto);
    }

    @GetMapping("public/books/{bookId}")
    public ResponseEntity<ApiResponse<BookDetailResponseDto>> getBookById(@PathVariable Long bookId) {
        BookDetailResponseDto responseDto = bookService.getBookById(bookId);
        return ApiResponse.onSuccess(SuccessCode.GET_BOOK_SUCCESS, responseDto);
    }

    @GetMapping("/public/books")
    public ResponseEntity <ApiResponse<List<BookSummaryResponseDto>>> getAllBooks() {
        List<BookSummaryResponseDto> list = bookService.getAllBooks();
        return ApiResponse.onSuccess(SuccessCode.GET_BOOK_LIST_SUCCESS, list);
    }

    @PutMapping("books/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity <ApiResponse<BookUpdateResponseDto>> updateBook(@PathVariable Long bookId, @Valid @RequestBody BookRequestDto request,
                                                                          @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        BookUpdateResponseDto responseDto = bookService.updateBook(request, principal.getUserId(), bookId);
        return ApiResponse.onSuccess(SuccessCode.UPDATE_BOOK_SUCCESS, responseDto);
    }

    @DeleteMapping("books/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity <ApiResponse<Void>> deleteBook(
            @PathVariable Long bookId,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        Long userId = principal.getUserId();
        bookService.deleteBook(bookId, userId);
        return ApiResponse.onSuccess(SuccessCode.DELETE_BOOK_SUCCESS, null);
    }
}
