package com.crunchit.housing_subscription.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService { // 알림 서비스
    private final FcmTokenService fcmTokenService;

    // 테스트용 알림 전송
    public void sendTestNotification(Long userId) {
        try {
            String userToken = fcmTokenService.getToken(userId);
            if (userToken == null) {
                return;
            }

            // FCM 메시지 생성
            Message message = Message.builder()
                    .setToken(userToken)
                    // 기본 알림 설정 (앱이 백그라운드일 때 시스템 알림으로 표시)
                    .setNotification(Notification.builder()
                            .setTitle("테스트 알림")
                            .setBody("FCM 알림이 잘 동작하나요?")
                            .build())
                    // 추가 데이터 (앱에서 활용 가능)
                    .putData("type", "TEST")
                    .putData("timestamp", String.valueOf(System.currentTimeMillis()))
                    .build();

            // FCM 으로 메시지 전송
            String response = FirebaseMessaging.getInstance().send(message);

        } catch (FirebaseMessagingException e) {
            throw new RuntimeException("Failed to send notification", e);
        }
    }
}
