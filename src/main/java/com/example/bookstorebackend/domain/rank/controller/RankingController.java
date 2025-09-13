package com.example.bookstorebackend.domain.rank.controller;

import com.example.bookstorebackend.domain.rank.service.RankingQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rankings")
public class RankingController {

    private final RankingQueryService rankingQueryService;

    @GetMapping("/viewed")
    public List<RankingQueryService.RankingItemDto> topViewed() {
        return rankingQueryService.getTopViewed();
    }

    @GetMapping("/purchased")
    public List<RankingQueryService.RankingItemDto> topPurchased() {
        return rankingQueryService.getTopPurchased();
    }
}