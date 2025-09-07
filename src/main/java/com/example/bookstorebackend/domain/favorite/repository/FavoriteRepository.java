package com.example.bookstorebackend.domain.favorite.repository;

import com.example.bookstorebackend.domain.book.entity.Book;
import com.example.bookstorebackend.domain.favorite.entity.Favorite;
import com.example.bookstorebackend.domain.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    boolean existsByUserAndBook(User user, Book book);

    @EntityGraph(attributePaths = {"book"})
    List<Favorite> findAllByUser_IdOrderByCreatedAtDesc(Long userId);

    Optional<Favorite> findByIdAndUser_Id(Long favoriteId, Long userId);

}