package com.example.bookstorebackend.domain.rank;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface BookStatsRepository extends JpaRepository<BookStats, Long> {

    @Modifying
    @Query(value = """
        INSERT INTO book_stats (book_id, view_all, purchase_all, last_viewed_at, updated_at)
        VALUES (:bookId, :delta, 0, NOW(), NOW())
        ON DUPLICATE KEY UPDATE
            view_all = view_all + VALUES(view_all),
            last_viewed_at = NOW(),
            updated_at = NOW()
        """, nativeQuery = true)
    void upsertViewAll(@Param("bookId") Long bookId, @Param("delta") long delta);

    @Modifying
    @Query(value = """
        INSERT INTO book_stats (book_id, view_all, purchase_all, last_purchased_at, updated_at)
        VALUES (:bookId, 0, :delta, NOW(), NOW())
        ON DUPLICATE KEY UPDATE
            purchase_all = purchase_all + VALUES(purchase_all),
            last_purchased_at = NOW(),
            updated_at = NOW()
        """, nativeQuery = true)
    void upsertPurchaseAll(@Param("bookId") Long bookId, @Param("delta") long delta);
}