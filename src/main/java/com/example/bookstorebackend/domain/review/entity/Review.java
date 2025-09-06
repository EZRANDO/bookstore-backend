package com.example.bookstorebackend.domain.review.entity;

import com.example.bookstorebackend.common.entity.BaseEntity;
import com.example.bookstorebackend.domain.book.entity.Book;
import com.example.bookstorebackend.domain.review.dto.request.ReviewRequestDto;
import com.example.bookstorebackend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id",  nullable = false)
    private Book book;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private String comment;

    public static Review of(User user, Book book, ReviewRequestDto dto) {
        return Review.builder()
                .user(user)
                .book(book)
                .rating(dto.getRating())
                .comment(dto.getComment())
                .build();
    }
    public void updateReview(ReviewRequestDto dto) {
        this.rating = dto.getRating();
        this.comment = dto.getComment();
    }
}