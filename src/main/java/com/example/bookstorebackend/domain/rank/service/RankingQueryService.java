package com.example.bookstorebackend.domain.rank.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RankingQueryService {

    private final StringRedisTemplate redis;
    private final ObjectMapper objectMapper;

    private static final String SNAP_VIEW_5M     = "rank:view:5m";
    private static final String SNAP_PURCHASE_5M = "rank:purchase:5m";

    public List<RankingItemDto> getTopViewed() {
        return readSnapshot(SNAP_VIEW_5M);
    }

    public List<RankingItemDto> getTopPurchased() {
        return readSnapshot(SNAP_PURCHASE_5M);
    }

    //Redis에서 JSON문자열을 가져오고 값이 없으면 null반환
    private List<RankingItemDto> readSnapshot(String snapshotKey) {
        String json = redis.opsForValue().get(snapshotKey);
        if (json == null || json.isBlank()) {
            return List.of();
        }
        try {
            //[{"bookId":123,"score":456}, ...]
            return objectMapper.readValue(
                    json,
                    objectMapper.getTypeFactory()
                            .constructCollectionType(List.class, RankingItemDto.class)
            );
        } catch (Exception e) {
            return List.of();
        }
    }

    public record RankingItemDto(Long bookId, long score) {}
}