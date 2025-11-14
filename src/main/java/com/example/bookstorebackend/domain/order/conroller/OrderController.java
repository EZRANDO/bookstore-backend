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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "주문 생성 성공",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "주문 생성 성공 예시",
                                    value = """
                                    {
                                      "isSuccess": true,
                                      "message": "주문이 정상적으로 생성되었습니다.",
                                      "payload": {
                                        "orderId": 501
                                      }
                                    }
                                    """
                            )
                    }
            )
    )
    public ResponseEntity<ApiResponse<OrderBaseResponseDto>> createOrder(
            @Valid @RequestBody OrderRequestDto requestDto,
            @AuthenticationPrincipal(expression = "userId") Long userId
    ) {
        OrderBaseResponseDto body = orderService.createOrder(userId, requestDto);
        return ApiResponse.onSuccess(SuccessCode.CREATE_ORDER_SUCCESS, body);
    }


    @GetMapping("/items")
    @Operation(summary = "내 주문 항목 전체 조회", description = "사용자가 생성한 모든 주문 항목을 조회합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "주문 항목 조회 성공",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "주문 항목 전체 조회 성공 예시",
                                    value = """
                                    {
                                      "isSuccess": true,
                                      "message": "주문 항목 목록을 정상적으로 조회했습니다.",
                                      "payload": [
                                        {
                                          "orderItemId": 1,
                                          "bookId": 1,
                                          "quantity": 1,
                                          "totalAmount": 25000,
                                          "status": "CREATED"
                                        }
                                      ]
                                    }
                                    """
                            )
                    }
            )
    )
    public ResponseEntity<ApiResponse<List<OrderItemResponseDto>>> findAllOrderItems(
            @AuthenticationPrincipal(expression = "userId") Long userId
    ) {
        List<OrderItemResponseDto> body = orderService.findAllOrderItems(userId);
        return ApiResponse.onSuccess(SuccessCode.GET_ORDER_ITEMS_SUCCESS, body);
    }

    @PatchMapping("/{orderId}/status")
    @Operation(summary = "주문 상태 변경", description = "사용자 또는 관리자가 주문 상태를 변경합니다.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "주문 상태 변경 성공",
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    name = "주문 상태 변경 성공 예시",
                                    value = """
                                    {
                                      "isSuccess": true,
                                      "message": "주문 상태를 정상적으로 변경했습니다.",
                                      "payload": {
                                        "orderId": 501,
                                        "status": "SHIPPED",
                                        "updatedAt": "2025-09-15T22:07:15.7325804"
                                      }
                                    }
                                    """
                            )
                    }
            )
    )
    public ResponseEntity<ApiResponse<OrderUpdateResponseDto>> updateOrderStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody OrderStatusUpdateRequestDto requestDto,
            @AuthenticationPrincipal(expression = "userId") Long userId
    ) {
        OrderUpdateResponseDto body = orderService.updateOrder(requestDto, orderId, userId);
        return ApiResponse.onSuccess(SuccessCode.UPDATE_ORDER_STATUS_SUCCESS, body);
    }
}