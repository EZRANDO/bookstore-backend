package com.example.bookstorebackend.domain.discount.repository;

import com.example.bookstorebackend.domain.discount.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {

}
