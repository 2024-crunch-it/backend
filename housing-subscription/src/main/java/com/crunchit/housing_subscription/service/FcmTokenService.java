package com.crunchit.housing_subscription.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
@Service
@RequiredArgsConstructor
public class FcmTokenService { // Redis 를 사용한 FCM 토큰 관리 서비스
    private final RedisTemplate<String, Object> redisTemplate;

    // Redis 키 prefix (fcm:user:userId 형태로 저장)
    private static final String FCM_TOKEN_PREFIX = "fcm:user:";

    // FCM 토큰 저장
    public void saveToken(Long userId, String fcmToken) {
        String key = FCM_TOKEN_PREFIX + userId;
        redisTemplate.opsForValue().set(key, fcmToken, 30, TimeUnit.DAYS);  // 30일 후 만료
    }

    // userID를 이용해 사용자 FCM 토큰 조회
    public String getToken(Long userId) {
        String key = FCM_TOKEN_PREFIX + userId;
        return (String) redisTemplate.opsForValue().get(key);
    }

    // userId를 이용해 사용자 FCM 토큰 삭제
    public void removeToken(Long userId) {
        String key = FCM_TOKEN_PREFIX + userId;
        redisTemplate.delete(key);
    }
}

