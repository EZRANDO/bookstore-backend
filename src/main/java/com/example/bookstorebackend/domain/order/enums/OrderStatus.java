package com.example.bookstorebackend.domain.order.enums;

public enum OrderStatus {
    CREATED,    //주문 생성
    SHIPPED,    //배송 시작
    DELIVERED,  //배송 완료
    CANCELED;

    public static boolean canTransit(OrderStatus from, OrderStatus to) {
        return switch (from) {
            case CREATED -> (to == SHIPPED || to == CANCELED);
            case SHIPPED -> (to == DELIVERED);
            case DELIVERED -> false;
            case CANCELED -> false;
        };
    }
}

