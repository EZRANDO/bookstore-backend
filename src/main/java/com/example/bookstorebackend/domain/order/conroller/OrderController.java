package com.example.bookstorebackend.domain.order.conroller;

import com.example.bookstorebackend.common.enums.SuccessCode;
import com.example.bookstorebackend.common.response.ApiResponse;
import com.example.bookstorebackend.domain.order.dto.request.OrderRequestDto;
import com.example.bookstorebackend.domain.order.dto.request.OrderStatusUpdateRequestDto;
import com.example.bookstorebackend.domain.order.dto.response.OrderBaseResponseDto;
import com.example.bookstorebackend.domain.order.dto.response.OrderItemResponseDto;
import com.example.bookstorebackend.domain.order.dto.response.OrderUpdateResponseDto;
import com.example.bookstorebackend.domain.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Orders", description = "주문 API")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "주문 생성", description = "사용자의 장바구니 항목을 기반으로 주문을 생성합니다.")
    public ResponseEntity<ApiResponse<OrderBaseResponseDto>> createOrder(
            @Valid @RequestBody OrderRequestDto requestDto,
            @AuthenticationPrincipal(expression = "userId") Long userId
    ) {
        OrderBaseResponseDto body = orderService.createOrder(userId, requestDto);
        return ApiResponse.onSuccess(SuccessCode.CREATE_ORDER_SUCCESS, body);
    }

    @GetMapping("/items")
    @Operation(summary = "내 주문 항목 전체 조회", description = "사용자가 생성한 모든 주문 항목을 조회합니다.")
    public ResponseEntity<ApiResponse<List<OrderItemResponseDto>>> findAllOrderItems(
            @AuthenticationPrincipal(expression = "userId") Long userId
    ) {
        List<OrderItemResponseDto> body = orderService.findAllOrderItems(userId);
        return ApiResponse.onSuccess(SuccessCode.GET_ORDER_ITEMS_SUCCESS, body);
    }

    //이 경우 사용자와 관리자 로직이 뒤섞임. 사실상 분리 필요.
    @PatchMapping("/{orderId}/status")
    @Operation(summary = "주문 상태 변경", description = "사용자 또는 관리자가 주문 상태를 변경합니다.")
    public ResponseEntity<ApiResponse<OrderUpdateResponseDto>> updateOrderStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody OrderStatusUpdateRequestDto requestDto,
            @AuthenticationPrincipal(expression = "userId") Long userId
    ) {
        OrderUpdateResponseDto body = orderService.updateOrder(requestDto, orderId, userId);
        return ApiResponse.onSuccess(SuccessCode.UPDATE_ORDER_STATUS_SUCCESS, body);
    }
}