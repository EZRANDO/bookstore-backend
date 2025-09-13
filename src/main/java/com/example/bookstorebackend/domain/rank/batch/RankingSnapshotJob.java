package com.example.bookstorebackend.domain.rank.batch;

import com.example.bookstorebackend.domain.rank.repository.BookStatsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RankingSnapshotJob {

    private final StringRedisTemplate redis;
    private final BookStatsRepository bookStatsRepository;

    private static final String Z_VIEW_ALL     = "z:view:all";
    private static final String Z_PURCHASE_ALL = "z:purchase:all";

    private static final String SNAP_VIEW_5M     = "rank:view:5m";
    private static final String SNAP_PURCHASE_5M = "rank:purchase:5m";

    //이 기준 시간마다 새 실행이 시작됨.
    //같은 객체 내부 호출이면 트랜잭션을 여기에 붙여야 함.
    @Scheduled(fixedRate = 300_000, initialDelay = 10_000)
    @Transactional
    public void runAll() {
        snapshotTopN(Z_VIEW_ALL, SNAP_VIEW_5M, 10);
        snapshotTopN(Z_PURCHASE_ALL, SNAP_PURCHASE_5M, 10);

        mergeToDb();
    }
    //스케줄 어노테이션을 또 붙이면 별도 호출되고, 트랜잭션이 적용되지 않음.
    private void mergeToDb() {
        mergeZset(Z_VIEW_ALL, true);
        mergeZset(Z_PURCHASE_ALL, false);
    }

    private void snapshotTopN(String zsetKey, String snapshotKey, int topN) {
        //Redis ZSet에서 Top-N 조회 (높은 점수 순)
        Set<ZSetOperations.TypedTuple<String>> tuples =
                redis.opsForZSet().reverseRangeWithScores(zsetKey, 0, topN - 1);

        if (tuples == null || tuples.isEmpty()) {
            return; //데이터 없으면 바로 종료되어야.
        }
        List<Map<String, Object>> rows = new ArrayList<>();
        for (ZSetOperations.TypedTuple<String> tuple : tuples) {
            if (tuple.getValue() == null || tuple.getScore() == null) continue;

            String member = tuple.getValue();
            Long bookId = parseBookId(member);
            long score = tuple.getScore().longValue();

            rows.add(Map.of("bookId", bookId, "score", score));
        }

        try {
            String json = new ObjectMapper().writeValueAsString(rows);
            redis.opsForValue().set(snapshotKey, json);
            //TTL을 주면 일정 시간 후 자동 삭제
            redis.expire(snapshotKey, Duration.ofMinutes(10));

        } catch (JsonProcessingException e) {
            throw new IllegalStateException("스냅샷 직렬화 실패", e);
        }
    }

    private void mergeZset(String key, boolean isView) {
        Set<ZSetOperations.TypedTuple<String>> tuples =
                redis.opsForZSet().popMax(key, 1000); // 1000개씩 가져오고 삭제

        if (tuples == null || tuples.isEmpty()) return;

        for (ZSetOperations.TypedTuple<String> t : tuples) {
            Long bookId = parseBookId(t.getValue());
            long delta  = t.getScore().longValue();

            if (isView) {
                bookStatsRepository.upsertViewAll(bookId, delta);
            } else {
                bookStatsRepository.upsertPurchaseAll(bookId, delta);
            }
        }
    }

    private Long parseBookId(String member) {
        int idx = member.indexOf(':');

        return Long.parseLong(idx >= 0 ? member.substring(idx + 1) : member);
    }
}
