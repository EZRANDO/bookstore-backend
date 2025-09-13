package com.example.bookstorebackend.domain.order.event;

import java.util.List;

public record PurchaseConfirmedEvent(Long orderId, List<Item> items)
{ public record Item(Long bookId, long quantity) {} }