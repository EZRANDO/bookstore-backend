package com.example.bookstorebackend.domain.rank.controller;

import com.example.bookstorebackend.domain.rank.service.StatsCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stats")
@Tag(name = "Stats", description = "통계 기록 API")
public class StatsController {

    private final StatsCommandService statsCommandService;

    //조회 이벤트 기록 bookId 한 번 조회할 때마다 + 이것도 비동기처리 하는게 좋긴함.
    @PostMapping("/view/{bookId}")
    @Operation(summary = "도서 조회 기록", description = "bookId별 조회 수를 +1 증가시킵니다.")
    public void recordView(@PathVariable Long bookId) {
        statsCommandService.recordView(bookId);
        //비동기 처리로직으로 대체됨
        //구매 이벤트 기록 결제 확정 시 수량만큼 +
//    @PostMapping("/purchase")
//    public void recordPurchase(@RequestBody PurchaseReq req) {
//        statsCommandService.recordPurchased(req.bookId(), req.quantity());
//    }
        //취소,환불 수량만큼 -
//    @PostMapping("/purchase/cancel")
//    public void adjustPurchase(@RequestBody PurchaseReq req) {
//        statsCommandService.adjustPurchase(req.bookId(), req.quantity());
//    }

//    public record PurchaseReq(Long bookId, long quantity) {}
    }
}
