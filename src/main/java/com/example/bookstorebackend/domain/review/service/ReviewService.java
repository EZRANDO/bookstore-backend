package com.example.bookstorebackend.domain.review.service;

import com.example.bookstorebackend.common.enums.ErrorCode;
import com.example.bookstorebackend.common.exception.CustomException;
import com.example.bookstorebackend.domain.book.entity.Book;
import com.example.bookstorebackend.domain.book.repository.BookRepository;
import com.example.bookstorebackend.domain.order.enums.OrderStatus;
import com.example.bookstorebackend.domain.order.repository.OrderItemRepository;
import com.example.bookstorebackend.domain.review.dto.request.ReviewRequestDto;
import com.example.bookstorebackend.domain.review.dto.response.ReviewBaseResponseDto;
import com.example.bookstorebackend.domain.review.dto.response.ReviewResponseDto;
import com.example.bookstorebackend.domain.review.dto.response.ReviewUpdateResponseDto;
import com.example.bookstorebackend.domain.review.entity.Review;
import com.example.bookstorebackend.domain.review.repository.ReviewRepository;
import com.example.bookstorebackend.domain.user.entity.User;
import com.example.bookstorebackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    @Transactional
    public ReviewBaseResponseDto createReview(ReviewRequestDto reviewRequestDto, Long userId, Long bookId) {
        User user = validUser(userId);

        //리뷰는 1개만 작성할 수 있음.
        if (reviewRepository.existsByUser_IdAndBook_Id(userId, bookId)) {
            throw new CustomException(ErrorCode.REVIEW_ALREADY_EXISTS);
        }

        //주문이 완료된 상태인지
        if (!orderItemRepository.existsPurchased(userId, bookId, OrderStatus.DELIVERED)) {
            throw new CustomException(ErrorCode.REVIEW_NOT_ALLOWED_NO_PURCHASE);
        }

        //주문한 목록의 book이 있는지 확인
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

        Review review = Review.of(user, book, reviewRequestDto);

        reviewRepository.save(review);

        return ReviewBaseResponseDto.from(review);
    }

    public List<ReviewResponseDto> findAllReviews(Long userId) {
        User user = validUser(userId);

        List<Review> reviews = reviewRepository.findAllByUser_IdOrderByCreatedAtDesc(user.getId());

        return reviews.stream()
                .map(ReviewResponseDto::from)
                .toList();
    }

    public ReviewResponseDto getReviewById(Long userId, Long reviewId) {
        validUser(userId);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        return ReviewResponseDto.from(review);
    }

    @Transactional
    public ReviewUpdateResponseDto updateReview(ReviewRequestDto reviewRequestDto, Long userId, Long reviewId) {
        validUser(userId);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        if (!review.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.FORBIDDEN_REVIEW_ACCESS);
        }

        review.updateReview(reviewRequestDto);

        return ReviewUpdateResponseDto.from(review);
    }

    @Transactional
    public void deleteReview(Long userId, Long reviewId) {
        validUser(userId);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

        if (!review.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.FORBIDDEN_REVIEW_ACCESS);
        }

        reviewRepository.delete(review);
    }

    public User validUser(Long userId) {
        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return user;
    }
}


