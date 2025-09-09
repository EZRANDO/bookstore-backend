package com.example.bookstorebackend.domain.order.conroller;

import com.example.bookstorebackend.common.enums.SuccessCode;
import com.example.bookstorebackend.common.response.ApiResponse;
import com.example.bookstorebackend.domain.order.dto.request.OrderRequestDto;
import com.example.bookstorebackend.domain.order.dto.request.OrderStatusUpdateRequestDto;
import com.example.bookstorebackend.domain.order.dto.response.OrderBaseResponseDto;
import com.example.bookstorebackend.domain.order.dto.response.OrderItemResponseDto;
import com.example.bookstorebackend.domain.order.dto.response.OrderUpdateResponseDto;
import com.example.bookstorebackend.domain.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderBaseResponseDto>> createOrder(
            @Valid @RequestBody OrderRequestDto requestDto,
            @AuthenticationPrincipal(expression = "userId") Long userId
    ) {
        OrderBaseResponseDto body = orderService.createOrder(userId, requestDto);
        return ApiResponse.onSuccess(SuccessCode.CREATE_ORDER_SUCCESS, body);
    }

    @GetMapping("/items")
    public ResponseEntity<ApiResponse<List<OrderItemResponseDto>>> findAllOrderItems(
            @AuthenticationPrincipal(expression = "userId") Long userId
    ) {
        List<OrderItemResponseDto> body = orderService.findAllOrderItems(userId);
        return ApiResponse.onSuccess(SuccessCode.GET_ORDER_ITEMS_SUCCESS, body);
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderUpdateResponseDto>> updateOrderStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody OrderStatusUpdateRequestDto requestDto,
            @AuthenticationPrincipal(expression = "userId") Long userId
    ) {
        OrderUpdateResponseDto body = orderService.updateOrder(requestDto, orderId, userId);
        return ApiResponse.onSuccess(SuccessCode.UPDATE_ORDER_STATUS_SUCCESS, body);
    }
}