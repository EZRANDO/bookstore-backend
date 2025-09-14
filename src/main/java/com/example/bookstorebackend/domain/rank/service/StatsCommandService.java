package com.example.bookstorebackend.domain.rank.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsCommandService {

    //Redis에 집계 증분 기록
    private final StringRedisTemplate redis;

    //정렬 집합 키 이름을 상수로 관리.
    private static final String Z_VIEW_ALL     = "z:view:all";
    private static final String Z_PURCHASE_ALL = "z:purchase:all";
    //멤버 키
    private static String member(Long bookId) { return "book:" + bookId; }

    //조회
    //특정 도서 조히 1회 발생 view키에서 책 멤버 점수 +1증가시킴
    public void recordView(Long bookId) {
        redis.opsForZSet().incrementScore(Z_VIEW_ALL, member(bookId), 1D);
    }

    //구매
    public void recordPurchased(Long bookId, long quantity) {
        if (quantity <= 0) return;
        redis.opsForZSet().incrementScore(Z_PURCHASE_ALL, member(bookId), (double) quantity);
    }

    //취소시 음수로 호출하여 수 조정.
    public void adjustPurchase(Long bookId, long quantity) {
        if (quantity <= 0) return;
        redis.opsForZSet().incrementScore(Z_PURCHASE_ALL, member(bookId), - (double) quantity);
    }
}
