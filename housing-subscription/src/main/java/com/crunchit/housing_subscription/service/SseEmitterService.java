package com.crunchit.housing_subscription.service;

import com.crunchit.housing_subscription.dto.response.NotificationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SseEmitterService { // 실시간 연결 관리 및 SSE 알림

    // SSE 용 emitter 를 저장하는 맵
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    // SSE 연결 추가 (SSE 구독)
    public SseEmitter add(Long userId) {
        // 1시간 타임아웃 설정
        SseEmitter emitter = new SseEmitter(TimeUnit.MINUTES.toMillis(60));
        this.emitters.put(userId, emitter);

        // emitter 생성과 사용자 구독
        emitter.onCompletion(() -> {
            log.info("SSE connection completed for user: {}", userId);
            this.emitters.remove(userId);
        });
        // SSE 연결 종료 시 자동 제거
        emitter.onTimeout(() -> {
            log.info("SSE connection timeout for user: {}", userId);
            emitter.complete(); // 명시적 호출로 SSE 연결을 완전히 닫음.
            this.emitters.remove(userId);
        });

        // 연결 즉시 더미 이벤트 전송 (연결 유지를 위해)
        try {
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("connected!"));
        } catch (IOException e) {
            log.error("Error sending initial SSE event", e);
            emitter.completeWithError(e);
        }
        return emitter;
    }

    // 특정 사용자에게 실시간 알림 전송
    public void sendToUser(Long userId, NotificationDto notification) {
        SseEmitter emitter = this.emitters.get(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("notification")  // 이벤트 이름을 notification 으로
                        .data(notification));  // 알림 데이터 전송
            } catch (IOException e) {
                log.error("Error sending SSE event to user: {}", userId, e);
                emitter.completeWithError(e);
                this.emitters.remove(userId);
            }
        }
    }
}
