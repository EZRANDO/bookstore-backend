package com.example.bookstorebackend.domain.discount.service;

import com.example.bookstorebackend.common.enums.ErrorCode;
import com.example.bookstorebackend.common.exception.CustomException;
import com.example.bookstorebackend.domain.book.entity.Book;
import com.example.bookstorebackend.domain.book.repository.BookRepository;
import com.example.bookstorebackend.domain.discount.dto.DiscountRequestDto;
import com.example.bookstorebackend.domain.discount.dto.DiscountResponseDto;
import com.example.bookstorebackend.domain.discount.dto.DiscountSummaryResponseDto;
import com.example.bookstorebackend.domain.discount.entity.Discount;
import com.example.bookstorebackend.domain.discount.repository.DiscountRepository;
import com.example.bookstorebackend.domain.user.entity.User;
import com.example.bookstorebackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional
    public DiscountResponseDto createDiscount(DiscountRequestDto discountRequestDto, Long userId, Long bookId) {
        validUser(userId);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

        Discount discount = Discount.of(book,discountRequestDto);

        Discount saveDiscount = discountRepository.save(discount);

        return DiscountResponseDto.from(saveDiscount);
    }

    //dto수정
    public List<DiscountSummaryResponseDto> getAllDiscounts() {
        return discountRepository.findAll()
                .stream()
                .map(DiscountSummaryResponseDto::from)
                .collect(Collectors.toList());
    }

    public DiscountSummaryResponseDto getDiscountById(Long discountId) {
        Discount discount = discountRepository.findById(discountId)
                .orElseThrow(() -> new CustomException(ErrorCode.DISCOUNT_NOT_FOUND));

        return DiscountSummaryResponseDto.from(discount);
    }

    public User validUser(Long userId) {
        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return user;
    }
}
