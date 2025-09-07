package com.example.bookstorebackend.domain.favorite.service;

import com.example.bookstorebackend.common.enums.ErrorCode;
import com.example.bookstorebackend.common.exception.CustomException;
import com.example.bookstorebackend.domain.book.entity.Book;
import com.example.bookstorebackend.domain.book.repository.BookRepository;
import com.example.bookstorebackend.domain.favorite.dto.response.FavoriteResponseDto;
import com.example.bookstorebackend.domain.favorite.dto.response.FavoriteSummaryResponseDto;
import com.example.bookstorebackend.domain.favorite.entity.Favorite;
import com.example.bookstorebackend.domain.favorite.repository.FavoriteRepository;
import com.example.bookstorebackend.domain.user.entity.User;
import com.example.bookstorebackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional
    public FavoriteResponseDto createFavorite(Long userId, Long bookId) {

        User user = validUser(userId);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

        if (favoriteRepository.existsByUserAndBook(user, book)) {
            throw new CustomException(ErrorCode.ALREADY_FAVORITE);
        }

        Favorite favorite = Favorite.createFromBook(user, book);

        favoriteRepository.save(favorite);

        bookRepository.incrementFavoriteCount(bookId);

        return FavoriteResponseDto.from(favorite);
    }

    public List<FavoriteSummaryResponseDto> getAllFavorites(Long userId) {

        validUser(userId);

        return favoriteRepository.findAllByUser_IdOrderByCreatedAtDesc(userId)
                .stream()
                .map(FavoriteSummaryResponseDto::from)
                .toList();
    }

    @Transactional
    public void deleteFavorite(Long userId, Long favoriteId) {

        User user = validUser(userId);

        Favorite favorite = favoriteRepository.findByIdAndUser_Id(favoriteId, user.getId())
                .orElseThrow(()-> new CustomException(ErrorCode.FAVORITE_NOT_FOUND));

        Long bookId = favorite.getBook().getId();

        favoriteRepository.delete(favorite);
        bookRepository.decrementFavoriteCount(bookId);
    }

    public User validUser(Long userId) {
        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return user;
    }
}
