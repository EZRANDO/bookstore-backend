package com.example.bookstorebackend.domain.rank;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RankingQueryService {

    private final StringRedisTemplate redis;

    private static final String SNAP_VIEW_5M     = "rank:view:5m";
    private static final String SNAP_PURCHASE_5M = "rank:purchase:5m";

    public List<RankingItemDto> getTopViewed() {
        return readSnapshot(SNAP_VIEW_5M);
    }

    public List<RankingItemDto> getTopPurchased() {
        return readSnapshot(SNAP_PURCHASE_5M);
    }

    private List<RankingItemDto> readSnapshot(String snapshotKey) {
        String json = redis.opsForValue().get(snapshotKey);
        if (json == null || json.isBlank()) return List.of();

        try {
            //{ "bookId": 123, "score": 456 }
            ObjectMapper om = new ObjectMapper();
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> rows =
                    om.readValue(json, List.class);

            List<RankingItemDto> result = new ArrayList<>(rows.size());
            for (Map<String, Object> row : rows) {
                Long bookId = ((Number) row.get("bookId")).longValue();
                long score  = ((Number) row.get("score")).longValue();
                result.add(new RankingItemDto(bookId, score));
            }
            return result;
        } catch (Exception e) {
            //파싱 실패 시 빈 목록
            return List.of();
        }
    }

    public record RankingItemDto(Long bookId, long score) {}
}