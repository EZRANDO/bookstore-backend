package com.example.bookstorebackend.domain.discount.entity;

import com.example.bookstorebackend.common.entity.BaseEntity;
import com.example.bookstorebackend.domain.book.entity.Book;
import com.example.bookstorebackend.domain.discount.dto.DiscountRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "discount")
public class Discount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Book book;

    @Column(nullable = false, precision = 5, scale = 2)
    private Double discountRate;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column( nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private Boolean isValid = true;

    public static Discount of(Book book, DiscountRequestDto discountRequestDto) {
        return Discount.builder()
                .book(book)
                .discountRate(discountRequestDto.getDiscountRate())
                .startDate(discountRequestDto.getStartAt())
                .endDate(discountRequestDto.getEndAt())
                .isValid(true)
                .build();
    }
}

