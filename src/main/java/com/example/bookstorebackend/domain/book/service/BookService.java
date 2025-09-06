package com.example.bookstorebackend.domain.book.service;

import com.example.bookstorebackend.common.enums.ErrorCode;
import com.example.bookstorebackend.common.exception.CustomException;
import com.example.bookstorebackend.domain.book.dto.request.BookRequestDto;
import com.example.bookstorebackend.domain.book.dto.response.BookDetailResponseDto;
import com.example.bookstorebackend.domain.book.dto.response.BookResponseDto;
import com.example.bookstorebackend.domain.book.dto.response.BookSummaryResponseDto;
import com.example.bookstorebackend.domain.book.dto.response.BookUpdateResponseDto;
import com.example.bookstorebackend.domain.book.entity.Book;
import com.example.bookstorebackend.domain.book.repository.BookRepository;
import com.example.bookstorebackend.domain.user.entity.User;
import com.example.bookstorebackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final UserRepository userRepository;

    private final BookRepository bookRepository;

    @Transactional
    public BookResponseDto createBook(BookRequestDto bookCreateRequestDto, Long userId) {
        validUser(userId);

        Book book = Book.createFromBook(bookCreateRequestDto);

        Book saveBook = bookRepository.save(book);

        return BookResponseDto.from(saveBook);
    }

    //response바꿔야 함.
    public List<BookSummaryResponseDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookSummaryResponseDto::from)
                .collect(Collectors.toList());
    }


    public BookDetailResponseDto getBookById(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

        return BookDetailResponseDto.from(book);
    }

    @Transactional
    public BookUpdateResponseDto updateBook(BookRequestDto bookCreateRequestDto, Long userId, Long bookId) {
        validUser(userId);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

        book.updateBook(bookCreateRequestDto);

        return BookUpdateResponseDto.from(book);
    }

    @Transactional
    public void deleteBook(Long bookId, Long userId) {
        validUser(userId);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

        bookRepository.delete(book);
    }


    public User validUser(Long userId) {
        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return user;
    }
}
