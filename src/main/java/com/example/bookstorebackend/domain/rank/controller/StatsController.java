package com.example.bookstorebackend.domain.rank.controller;

import com.example.bookstorebackend.domain.rank.service.StatsCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stats")
public class StatsController {

    private final StatsCommandService statsCommandService;

    //조회 이벤트 기록 bookId 한 번 조회할 때마다 +
    @PostMapping("/view/{bookId}")
    public void recordView(@PathVariable Long bookId) {
        statsCommandService.recordView(bookId);
    }

    //비동기 처리로직으로 대체
    //구매 이벤트 기록 결제 확정 시 수량만큼 +
//    @PostMapping("/purchase")
//    public void recordPurchase(@RequestBody PurchaseReq req) {
//        statsCommandService.recordPurchased(req.bookId(), req.quantity());
//    }
    //취소/환불 수량만큼 -
//    @PostMapping("/purchase/cancel")
//    public void adjustPurchase(@RequestBody PurchaseReq req) {
//        statsCommandService.adjustPurchase(req.bookId(), req.quantity());
//    }

//    public record PurchaseReq(Long bookId, long quantity) {}
}
