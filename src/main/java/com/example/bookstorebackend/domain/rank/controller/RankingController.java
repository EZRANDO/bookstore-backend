package com.example.bookstorebackend.domain.rank.controller;

import com.example.bookstorebackend.domain.rank.service.RankingQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rankings")
@Tag(name = "Rankings", description = "도서 랭킹 API")
public class RankingController {

    private final RankingQueryService rankingQueryService;

    @GetMapping("/viewed")
    @Operation(summary = "조회수 랭킹", description = "조회수가 높은 도서 순으로 반환합니다.")
    public List<RankingQueryService.RankingItemDto> topViewed() {
        return rankingQueryService.getTopViewed();
    }

    @GetMapping("/purchased")
    @Operation(summary = "구매수 랭킹", description = "구매수가 높은 도서 순으로 반환합니다.")
    public List<RankingQueryService.RankingItemDto> topPurchased() {
        return rankingQueryService.getTopPurchased();
    }
}