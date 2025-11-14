package com.example.bookstorebackend.domain.cart.controller;

import com.example.bookstorebackend.common.enums.SuccessCode;
import com.example.bookstorebackend.common.response.ApiResponse;
import com.example.bookstorebackend.domain.cart.dto.request.CartRequestDto;
import com.example.bookstorebackend.domain.cart.dto.response.CartBaseResponseDto;
import com.example.bookstorebackend.domain.cart.dto.response.CartItemResponseDto;
import com.example.bookstorebackend.domain.cart.service.CartService;
import com.example.bookstorebackend.security.principal.CustomUserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "장바구니 항목 생성 성공",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "장바구니 항목 추가 성공 예시",
                                    value = """
                                    {
                                      "isSuccess": true,
                                      "message": "장바구니에 도서가 성공적으로 추가되었습니다.",
                                      "payload": {
                                        "cartId": 3001
                                      }
                                    }
                                    """
                            )
                    }
            )
    )
    public ResponseEntity<ApiResponse<CartBaseResponseDto>> addItem(
            @Valid @RequestBody CartRequestDto request,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        CartBaseResponseDto responseDto = cartService.createCart(request, principal.getUserId());
        return ApiResponse.onSuccess(SuccessCode.ADD_CART_ITEM_SUCCESS, responseDto);
    }

    @GetMapping("/items")
    @Operation(summary = "장바구니 항목 전체 조회", description = "사용자의 장바구니에 담긴 모든 항목을 조회합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "장바구니 항목 조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "장바구니 전체 조회 성공 예시",
                                    value = """
                                    {
                                      "isSuccess": true,
                                      "message": "장바구니 항목 목록이 성공적으로 조회되었습니다.",
                                      "payload": [
                                        {
                                          "cartItemId": 201,
                                          "bookId": 10,
                                          "quantity": 2
                                        }
                                      ]
                                    }
                                    """
                            )
                    }
            )
    )
    public ResponseEntity<ApiResponse<List<CartItemResponseDto>>> findAllItems(
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        List<CartItemResponseDto> responseDto = cartService.findAllCartItems(principal.getUserId());
        return ApiResponse.onSuccess(SuccessCode.GET_CART_ITEMS_SUCCESS, responseDto);
    }


    @PutMapping("/items")
    @Operation(summary = "장바구니 수량 변경", description = "사용자의 장바구니에 담긴 도서의 수량을 수정합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "장바구니 항목 수량 변경 성공",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "장바구니 수량 변경 성공 예시",
                                    value = """
                                    {
                                      "isSuccess": true,
                                      "message": "장바구니 항목이 성공적으로 수정되었습니다.",
                                      "payload": {
                                        "cartId": 3001
                                      }
                                    }
                                    """
                            )
                    }
            )
    )
    public ResponseEntity<ApiResponse<CartBaseResponseDto>> updateItemQuantity(
            @Valid @RequestBody CartRequestDto request,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        CartBaseResponseDto responseDto = cartService.updateCart(request, principal.getUserId());
        return ApiResponse.onSuccess(SuccessCode.UPDATE_CART_ITEM_SUCCESS, responseDto);
    }

    @DeleteMapping("/items/{bookId}")
    @Operation(summary = "장바구니 항목 삭제", description = "사용자의 장바구니에서 특정 도서를 삭제합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "204",
            description = "장바구니 항목 삭제 성공",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "장바구니 삭제 성공 예시",
                                    value = """
                                    {
                                    }
                                    """
                            )
                    }
            )
    )
    public ResponseEntity<ApiResponse<Void>> deleteItem(
            @PathVariable Long bookId,
            @AuthenticationPrincipal CustomUserPrincipal principal
    ) {
        cartService.deleteCart(principal.getUserId(), bookId);
        return ApiResponse.onSuccess(SuccessCode.DELETE_CART_ITEM_SUCCESS, null);
    }
}