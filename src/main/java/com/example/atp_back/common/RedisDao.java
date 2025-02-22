package com.example.atp_back.common;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class RedisDao {
    private final RedisTemplate<String, String> redisTemplate;
    private static final String REALTIME_VIEW_KEY = "realtime:viewCount";

    // 단일 값 저장
    public void setValues(String key, String data) {
        redisTemplate.opsForValue().set(key, data);
    }

    // 리스트에 값 추가
    public void setValuesList(String key, String data) {
        redisTemplate.opsForList().rightPushAll(key, data);
    }

    // 리스트 값 가져오기
    public List<String> getValuesList(String key) {
        Long len = redisTemplate.opsForList().size(key);
        return len == 0 ? new ArrayList<>() : redisTemplate.opsForList().range(key, 0, len - 1);
    }


    // 단일 값 가져오기
    public String getValues(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 단일 값 증가 (조회수 증가에 사용)
    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    // 데이터 삭제
    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }


    /*실시간 인기 조회 포트폴리오 관련*/
    // 실시간 조회수 증가 (포트폴리오 ID를 스코어로, 현재 시간을 멤버로 저장)
    public void incrementRealtimeViewCount(Long portfolioIdx) {
        long now = Instant.now().getEpochSecond(); // 현재 시간을 초 단위로 가져옴
        redisTemplate.opsForZSet().incrementScore(REALTIME_VIEW_KEY, portfolioIdx.toString(), 1);
        redisTemplate.opsForZSet().add(REALTIME_VIEW_KEY, portfolioIdx.toString(), now);
    }

    // 실시간 인기 포트폴리오 조회 (상위 N개)
    public List<String> getTopNRealtimePortfolios(int topN) {
        return redisTemplate.opsForZSet()
                .reverseRange(REALTIME_VIEW_KEY, 0, topN - 1)
                .stream().toList();
    }

    // 슬라이딩 윈도우를 사용하여 2시간 이전 데이터 제거
    public void removeOldRealtimeData(int hours) {
        long cutoff = Instant.now().minusSeconds(hours * 3600).getEpochSecond();
        redisTemplate.opsForZSet().removeRangeByScore(REALTIME_VIEW_KEY, 0, cutoff);
    }
}
