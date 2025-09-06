package com.example.bookstorebackend.domain.cart.service;

import com.example.bookstorebackend.common.enums.ErrorCode;
import com.example.bookstorebackend.common.exception.CustomException;
import com.example.bookstorebackend.domain.book.entity.Book;
import com.example.bookstorebackend.domain.book.repository.BookRepository;
import com.example.bookstorebackend.domain.cart.dto.request.UpdateCartItemRequestDto;
import com.example.bookstorebackend.domain.cart.entity.Cart;
import com.example.bookstorebackend.domain.cart.entity.CartItem;
import com.example.bookstorebackend.domain.cart.repository.CartItemRepository;
import com.example.bookstorebackend.domain.cart.repository.CartRepository;
import com.example.bookstorebackend.domain.cart.dto.request.CartRequestDto;
import com.example.bookstorebackend.domain.cart.dto.response.CartBaseResponseDto;
import com.example.bookstorebackend.domain.cart.dto.response.CartItemResponseDto;
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
public class CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final BookRepository bookRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public CartBaseResponseDto createCart(CartRequestDto cartRequestDto, Long userId) {

        User user = validUser(userId);

        //도서 있는지 조회 필요
        Book book = bookRepository.findById(cartRequestDto.getBookId())
                .orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

        //카트 조회하고 없으면 생성해야 함.
        Cart cart = findOrCreateCart(user);

        //기존 도서 확인 이미 담겨있으면 수량만 갱신되어야.
        CartItem item = cartItemRepository.findByCartAndBook(cart, book)
                .map(cartItem -> { cartItem.increaseQuantity(cartRequestDto.getQuantity()); return cartItem; })
                .orElseGet(() -> CartItem.of(cart, book, cartRequestDto.getQuantity()));

        //저장
        CartItem saved = cartItemRepository.save(item);

        return CartBaseResponseDto.from(saved);
    }

    public List<CartItemResponseDto> findAllCartItems(Long userId) {

        User user = validUser(userId);

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(ErrorCode.CART_NOT_FOUND));

        return cartItemRepository.findAllByCart(cart)
                .stream()
                .map(CartItemResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public CartBaseResponseDto updateCart(UpdateCartItemRequestDto cartRequestDto, Long userId) {

        User user = validUser(userId);

        Book book = bookRepository.findById(cartRequestDto.getBookId())
                .orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(ErrorCode.CART_NOT_FOUND));

        CartItem cartItem = cartItemRepository.findByCartAndBook(cart, book)
                .orElseThrow(() -> new CustomException(ErrorCode.CART_ITEM_NOT_FOUND));

        cartItem.changeQuantity(cartRequestDto.getQuantity());

        return CartBaseResponseDto.from(cartItem);
    }

    @Transactional
    public void deleteCart(Long userId, Long bookId) {

        User user = validUser(userId);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(ErrorCode.CART_NOT_FOUND));

        CartItem cartItem = cartItemRepository.findByCartAndBook(cart, book)
                .orElseThrow(() -> new CustomException(ErrorCode.CART_ITEM_NOT_FOUND));

        cartItemRepository.delete(cartItem);
    }

    public User validUser(Long userId) {
        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return user;
    }

    private Cart findOrCreateCart(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .user(user)
                            .build();
                    return cartRepository.save(newCart);
                });
    }
}
