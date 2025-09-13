package com.example.bookstorebackend.domain.rank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "book_stats")
public class BookStats{

    @Id
    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Column(name = "view_all", nullable = false)
    private long viewAll;

    @Column(name = "purchase_all", nullable = false)
    private long purchaseAll;

    @Column(name = "last_viewed_at")
    private LocalDateTime lastViewedAt;

    @Column(name = "last_purchased_at")
    private LocalDateTime lastPurchasedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}