package com.example.bookstorebackend.domain.order.entity;

import com.example.bookstorebackend.common.entity.BaseEntity;
import com.example.bookstorebackend.common.enums.ErrorCode;
import com.example.bookstorebackend.common.exception.CustomException;
import com.example.bookstorebackend.domain.order.enums.OrderStatus;
import com.example.bookstorebackend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private Integer totalAmount;

    @Column(name = "order_date", nullable = false, updatable = false)
    private LocalDateTime orderDate;

    @PrePersist
    public void prePersist() {
        if (this.status == null) this.status = OrderStatus.CREATED;
        this.orderDate = LocalDateTime.now();
    }

    public static Order of(User user) {
        return Order.builder()
                .user(user)
                .status(OrderStatus.CREATED)
                .totalAmount(0)
                .build();
    }

    public void commitTotalAmount(int total) {
        //확정은 생성 직후 단계에서만 허용
        if (this.status != OrderStatus.CREATED) {
            throw new CustomException(ErrorCode.INVALID_ORDER_STATE_TRANSITION);
        }
        this.totalAmount = total;
    }

    public void changeStatus(OrderStatus next) {
        OrderStatus current = this.status;

        //같은 상태면 조용히 종료
        if (current == next) return;

        //전이 규칙
        if (!OrderStatus.canTransit(current, next)) {
            throw new CustomException(ErrorCode.INVALID_ORDER_STATUS_TRANSITION);
        }
        this.status = next;
    }
}