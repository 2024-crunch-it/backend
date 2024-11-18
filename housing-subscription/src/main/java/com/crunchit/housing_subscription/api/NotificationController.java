package com.crunchit.housing_subscription.api;

import com.crunchit.housing_subscription.dto.request.FcmTokenRequestDto;
import com.crunchit.housing_subscription.dto.response.NotificationDto;
import com.crunchit.housing_subscription.entity.User;
import com.crunchit.housing_subscription.repository.NotificationHistoryRepository;
import com.crunchit.housing_subscription.repository.UserRepository;
import com.crunchit.housing_subscription.service.FcmTokenService;
import com.crunchit.housing_subscription.service.NotificationService;
import com.crunchit.housing_subscription.service.SseEmitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController { // 알림 관련 API 컨트롤러
    private final FcmTokenService fcmTokenService;
    private final NotificationService notificationService;
    private final NotificationHistoryRepository notificationHistoryRepository;
    private final SseEmitterService sseEmitterService;
    private final UserRepository userRepository;

    // FCM 토큰 등록 API
    @PostMapping("/token")
    public ResponseEntity<Void> registerToken(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody FcmTokenRequestDto request) {
        System.out.println("Received FCM Token: " + request.getToken());
        fcmTokenService.saveToken(userId, request.getToken());
        return ResponseEntity.ok().build();
    }

    // 알림 목록 조회 API
    @GetMapping
    public ResponseEntity<List<NotificationDto>> getNotifications(
            @RequestHeader("X-USER-ID") Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<NotificationDto> notifications = notificationHistoryRepository
                .findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(NotificationDto::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(notifications);
    }

    // SSE 연결 설정 (SSE 구독)
    @GetMapping("/subscribe")
    public SseEmitter subscribe(@RequestHeader("X-USER-ID") Long userId) {
        return sseEmitterService.add(userId);
    }

    // 알림 읽음 처리
    // HTTP PATCH 메서드는 리소스의 일부 데이터를 수정할 때 사용, 읽음 상태만 업데이트
    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(
            @PathVariable Long notificationId,
            @RequestHeader("X-USER-ID") Long userId) {
        notificationService.markAsRead(notificationId, userId);
        return ResponseEntity.ok().build();
    }

    // FCM 토큰 삭제 API
    @DeleteMapping("/token")
    public ResponseEntity<Void> removeToken(
            @RequestHeader("X-USER-ID") Long userId) {
        fcmTokenService.removeToken(userId);
        return ResponseEntity.ok().build();
    }

    // 테스트용 알림 전송 API
    @PostMapping("/test")
    public ResponseEntity<Void> sendTestNotification(
            @RequestHeader("X-USER-ID") Long userId) {
        notificationService.sendTestNotification(userId);
        return ResponseEntity.ok().build();
    }
}
