package com.example.bookstorebackend.domain.cart.controller;

import com.example.bookstorebackend.common.enums.SuccessCode;
import com.example.bookstorebackend.common.response.ApiResponse;
import com.example.bookstorebackend.domain.cart.dto.request.CartRequestDto;
import com.example.bookstorebackend.domain.cart.dto.request.UpdateCartItemRequestDto;
import com.example.bookstorebackend.domain.cart.dto.response.CartBaseResponseDto;
import com.example.bookstorebackend.domain.cart.dto.response.CartItemResponseDto;
import com.example.bookstorebackend.domain.cart.service.CartService;
import com.example.bookstorebackend.security.principal.CustomUserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    public ResponseEntity<ApiResponse<CartBaseResponseDto>> addItem(@Valid @RequestBody CartRequestDto request,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        CartBaseResponseDto responseDto = cartService.createCart(request, principal.getUserId());
        return ApiResponse.onSuccess(SuccessCode.ADD_CART_ITEM_SUCCESS, responseDto);
    }

    @GetMapping("/items")
    public ResponseEntity<ApiResponse<List<CartItemResponseDto>>> findAllItems(
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        List<CartItemResponseDto> responseDto = cartService.findAllCartItems(principal.getUserId());
        return ApiResponse.onSuccess(SuccessCode.GET_CART_ITEMS_SUCCESS, responseDto);
    }

    @PutMapping("/items")
    public ResponseEntity<ApiResponse<CartBaseResponseDto>> updateItemQuantity(@Valid @RequestBody UpdateCartItemRequestDto request,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        CartBaseResponseDto responseDto = cartService.updateCart(request, principal.getUserId());
        return ApiResponse.onSuccess(SuccessCode.UPDATE_CART_ITEM_SUCCESS, responseDto);
    }

    @DeleteMapping("/items/{bookId}")
    public ResponseEntity<ApiResponse<Void>> deleteItem(@PathVariable Long bookId,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        cartService.deleteCart(principal.getUserId(), bookId);
        return ApiResponse.onSuccess(SuccessCode.DELETE_CART_ITEM_SUCCESS, null);
    }
}
