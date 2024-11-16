package com.crunchit.housing_subscription.api;

import com.crunchit.housing_subscription.dto.request.FcmTokenRequestDto;
import com.crunchit.housing_subscription.service.FcmTokenService;
import com.crunchit.housing_subscription.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
@CrossOrigin(origins = "http://localhost:5173")  // React 개발 서버 주소를 허용
public class NotificationController { // 알림 관련 API 컨트롤러
    private final FcmTokenService fcmTokenService;
    private final NotificationService notificationService;

    // FCM 토큰 등록 API
    @PostMapping("/token")
    public ResponseEntity<Void> registerToken(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody FcmTokenRequestDto request) {
        System.out.println("Received FCM Token: " + request.getToken());
        fcmTokenService.saveToken(userId, request.getToken());
        return ResponseEntity.ok().build();
    }

    // 테스트용 알림 전송 API
    @PostMapping("/test")
    public ResponseEntity<Void> sendTestNotification(
            @RequestHeader("X-USER-ID") Long userId) {
        notificationService.sendTestNotification(userId);
        return ResponseEntity.ok().build();
    }

    // FCM 토큰 삭제 API
    @DeleteMapping("/token")
    public ResponseEntity<Void> removeToken(
            @RequestHeader("X-USER-ID") Long userId) {
        fcmTokenService.removeToken(userId);
        return ResponseEntity.ok().build();
    }
}
