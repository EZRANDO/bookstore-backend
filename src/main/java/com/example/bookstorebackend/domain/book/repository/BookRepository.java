package com.example.bookstorebackend.domain.book.repository;

import com.example.bookstorebackend.domain.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findById(Long id);

    @Modifying
    @Query(value = "UPDATE book SET favorite_count = favorite_count + 1 WHERE id = :bookId", nativeQuery = true)
    void incrementFavoriteCount(@Param("bookId") Long bookId);

    @Modifying
    @Query("update Book b set b.favoriteCount = b.favoriteCount - 1 where b.id = :bookId and b.favoriteCount > 0")
    int decrementFavoriteCount(@Param("bookId") Long bookId);

}
