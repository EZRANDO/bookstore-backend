package com.example.bookstorebackend.domain.cart.controller;

import com.example.bookstorebackend.common.enums.SuccessCode;
import com.example.bookstorebackend.common.response.ApiResponse;
import com.example.bookstorebackend.domain.cart.dto.request.CartRequestDto;
import com.example.bookstorebackend.domain.cart.dto.response.CartBaseResponseDto;
import com.example.bookstorebackend.domain.cart.dto.response.CartItemResponseDto;
import com.example.bookstorebackend.domain.cart.service.CartService;
import com.example.bookstorebackend.security.principal.CustomUserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
@Tag(name = "Carts", description = "장바구니 API")
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    @Operation(summary = "장바구니 항목 추가", description = "사용자의 장바구니에 새로운 도서를 추가합니다.")
    public ResponseEntity<ApiResponse<CartBaseResponseDto>> addItem(@Valid @RequestBody CartRequestDto request,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        CartBaseResponseDto responseDto = cartService.createCart(request, principal.getUserId());
        return ApiResponse.onSuccess(SuccessCode.ADD_CART_ITEM_SUCCESS, responseDto);
    }

    @GetMapping("/items")
    @Operation(summary = "장바구니 항목 전체 조회", description = "사용자의 장바구니에 담긴 모든 항목을 조회합니다.")
    public ResponseEntity<ApiResponse<List<CartItemResponseDto>>> findAllItems(
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        List<CartItemResponseDto> responseDto = cartService.findAllCartItems(principal.getUserId());
        return ApiResponse.onSuccess(SuccessCode.GET_CART_ITEMS_SUCCESS, responseDto);
    }

    @PutMapping("/items")
    @Operation(summary = "장바구니 수량 변경", description = "사용자의 장바구니에 담긴 도서의 수량을 수정합니다.")
    public ResponseEntity<ApiResponse<CartBaseResponseDto>> updateItemQuantity(@Valid @RequestBody CartRequestDto request,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        CartBaseResponseDto responseDto = cartService.updateCart(request, principal.getUserId());
        return ApiResponse.onSuccess(SuccessCode.UPDATE_CART_ITEM_SUCCESS, responseDto);
    }


    @DeleteMapping("/items/{bookId}")
    @Operation(summary = "장바구니 항목 삭제", description = "사용자의 장바구니에서 특정 도서를 삭제합니다.")
    public ResponseEntity<ApiResponse<Void>> deleteItem(@PathVariable Long bookId,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        cartService.deleteCart(principal.getUserId(), bookId);
        return ApiResponse.onSuccess(SuccessCode.DELETE_CART_ITEM_SUCCESS, null);
    }
}
