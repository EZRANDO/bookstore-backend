package com.example.bookstorebackend.domain.order.service;

import com.example.bookstorebackend.common.enums.ErrorCode;
import com.example.bookstorebackend.common.exception.CustomException;
import com.example.bookstorebackend.domain.cart.entity.CartItem;
import com.example.bookstorebackend.domain.cart.repository.CartItemRepository;
import com.example.bookstorebackend.domain.order.dto.request.OrderItemRequestDto;
import com.example.bookstorebackend.domain.order.dto.request.OrderRequestDto;
import com.example.bookstorebackend.domain.order.dto.request.OrderStatusUpdateRequestDto;
import com.example.bookstorebackend.domain.order.dto.response.OrderBaseResponseDto;
import com.example.bookstorebackend.domain.order.dto.response.OrderItemResponseDto;
import com.example.bookstorebackend.domain.order.dto.response.OrderUpdateResponseDto;
import com.example.bookstorebackend.domain.order.entity.Order;
import com.example.bookstorebackend.domain.order.entity.OrderItem;
import com.example.bookstorebackend.domain.order.repository.OrderItemRepository;
import com.example.bookstorebackend.domain.order.repository.OrderRepository;
import com.example.bookstorebackend.domain.user.entity.User;
import com.example.bookstorebackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

//삭제는 일단 만들지 않았음.
@Service
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public OrderBaseResponseDto createOrder(Long userId, OrderRequestDto orderRequestDto) {

        User user = validUser(userId);

        List<CartItem> items = cartItemRepository.findAllWithCartAndBookByUser(user);
        if (items.isEmpty()) {
            throw new CustomException(ErrorCode.CART_EMPTY);
        }

        Order order = orderRepository.save(Order.of(user));

        //CartItem → OrderItem 생성
        List<OrderItem> orderItems = buildOrderItems(order, items, orderRequestDto);

        //주문항목 저장
        orderItemRepository.saveAll(orderItems);

        //총액 계산 상태 변경
        order.commitTotalAmount(calculateTotalAmount(orderItems)); //결제 이후에만 변경한다면 이 줄은 콜백 시점으로 이동

        //장바구니 비워짐
        cartItemRepository.deleteByCart_Id(items.getFirst().getCart().getId());
        return OrderBaseResponseDto.from(order);
    }

    public List<OrderItemResponseDto> findAllOrderItems (Long userId) {

        User user = validUser(userId);

        List<OrderItem> items = orderItemRepository.findAllWithOrderAndBookByUser(user);

        return items.stream()
                .map(OrderItemResponseDto::from)
                .toList();
    }

    //낙관적 락으로 동시 업데이트되는 문제를 해결 가능
    @Transactional
    public OrderUpdateResponseDto updateOrder(OrderStatusUpdateRequestDto requestDto, Long orderId, Long userId) {

        User user = validUser(userId);

        Order order = orderRepository.findByIdAndUser_Id(orderId, user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        order.changeStatus(requestDto.getStatus());

        return OrderUpdateResponseDto.from(order);
    }

    private int calculateTotalAmount(List<OrderItem> orderItems) {
        int total = 0;
        for (OrderItem oi : orderItems) {
            int price = oi.getBook().getPrice();
            int qty   = oi.getQuantity();

            try {
                total = Math.addExact(total, Math.multiplyExact(price, qty));
            } catch (ArithmeticException e) {
                throw new CustomException(ErrorCode.AMOUNT_OVERFLOW);
            }
        }
        return total;
    }

    //해시맵으로 중복순회를 제거하는 최적화 로직 필요.
    private List<OrderItem> buildOrderItems(Order order, List<CartItem> cartItems, OrderRequestDto orderRequestDto) {
        List<OrderItem> result = new ArrayList<>(cartItems.size());
        for (OrderItemRequestDto orderItem : orderRequestDto.getItems()) {
            CartItem ci = cartItems.stream()
                    .filter(c -> c.getBook().getId().equals(orderItem.getBookId()))
                    .findFirst()
                    .orElseThrow(() -> new CustomException(ErrorCode.CART_ITEM_NOT_FOUND));

            int cartQty  = ci.getQuantity();
            int orderQty = orderItem.getQuantity();

            if (cartQty <= 0) {
                throw new CustomException(ErrorCode.INVALID_ORDER_QUANTITY);
            }
            if (orderQty > cartQty) {
                throw new CustomException(ErrorCode.ORDER_QUANTITY_EXCEEDS_CART_QUANTITY);
            }
            //CartItem이 이미 Book을 들고 있으므로 재조회 불필요
            result.add(OrderItem.of(order, ci.getBook(), cartQty));
        }
        return result;
    }

    public User validUser(Long userId) {
        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return user;
    }
}
