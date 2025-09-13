package com.example.bookstorebackend.domain.rank.event;

import com.example.bookstorebackend.domain.order.event.PurchaseAdjustedEvent;
import com.example.bookstorebackend.domain.order.event.PurchaseConfirmedEvent;
import com.example.bookstorebackend.domain.rank.service.StatsCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StatsEventListener {

    private final StatsCommandService statsCommandService;

    @EventListener
    public void handlePurchaseConfirmed(PurchaseConfirmedEvent event) {
        log.info("구매 확정 이벤트 수신: orderId={}, items={}", event.orderId(), event.items().size());
        for (PurchaseConfirmedEvent.Item item : event.items()) {
            statsCommandService.recordPurchased(item.bookId(), item.quantity());
        }
    }

    @EventListener
    public void handlePurchaseAdjusted(PurchaseAdjustedEvent event) {
        log.info("구매 취소 이벤트 수신: orderId={}, items={}", event.orderId(), event.items().size());
        for (PurchaseAdjustedEvent.Item item : event.items()) {
            statsCommandService.adjustPurchase(item.bookId(), item.quantity());
        }
    }
}
