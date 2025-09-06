package com.example.bookstorebackend.domain.review.repository;

import com.example.bookstorebackend.domain.review.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByUser_IdAndBook_Id(Long userId, Long bookId);

    @EntityGraph(attributePaths = {"book"})
    List<Review> findAllByUser_IdOrderByCreatedAtDesc(Long userId);

}
