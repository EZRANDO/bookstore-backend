package com.example.bookstorebackend.domain.book.service;

import com.example.bookstorebackend.common.enums.ErrorCode;
import com.example.bookstorebackend.common.exception.CustomException;
import com.example.bookstorebackend.domain.book.dto.request.BookRequestDto;
import com.example.bookstorebackend.domain.book.dto.response.BookResponseDto;
import com.example.bookstorebackend.domain.book.entity.Book;
import com.example.bookstorebackend.domain.book.repository.BookRepository;
import com.example.bookstorebackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
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
    public BookResponseDto createBook (BookRequestDto bookCreateRequestDto) {

        Book book = Book.createFromBook(bookCreateRequestDto);

        Book saveBook = bookRepository.save(book);

        return BookResponseDto.from(saveBook);

    }


    public List<BookResponseDto> getAllBooks(Long bookId) {

        return bookRepository.findAllById(bookId)
                .stream()
                .map(BookResponseDto::from)
                .collect(Collectors.toList());

    }

    public BookResponseDto getBookById(Long bookId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

        return BookResponseDto.from(book);

    }

    @Transactional
    public BookResponseDto updateBook(BookRequestDto bookCreateRequestDto) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));



    }

    public User validUser(Long userId) {
        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_FOUND));
        return user;
    }



}
