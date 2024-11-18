package com.crunchit.housing_subscription.service;

import com.crunchit.housing_subscription.dto.response.NotificationDto;
import com.crunchit.housing_subscription.dto.response.NotificationMessageDto;
import com.crunchit.housing_subscription.entity.HousingAnnouncement;
import com.crunchit.housing_subscription.entity.NotificationHistory;
import com.crunchit.housing_subscription.entity.NotificationSchedule;
import com.crunchit.housing_subscription.repository.NotificationHistoryRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService { // 알림 서비스
    private final FcmTokenService fcmTokenService;
    private final NotificationScheduleService notificationScheduleService;
    private final NotificationHistoryRepository notificationHistoryRepository;
    private final SseEmitterService sseEmitterService;

    /**
     * 예약된 알림을 발송하는 스케줄러
     * 1분 간격으로 실행되며, 발송 시간이 된 알림들을 찾아서 발송
     */
    @Scheduled(fixedRate = 60000) // 1분마다 실행
    @Transactional
    public void sendScheduledNotifications() {
        // 현재 시간 기준으로 발송해야 할 알림 스케줄 목록 조회
        List<NotificationSchedule> schedules = notificationScheduleService.findSchedulesToSend();

        for (NotificationSchedule schedule : schedules) {
            try {
                // Redis 에서 사용자의 FCM 토큰 조회
                String userToken = fcmTokenService.getToken(schedule.getUser().getUserId());
                if (userToken == null) {
                    log.warn("FCM token not found for user: {}", schedule.getUser().getUserId());
                    continue;  // 토큰이 없으면 다음 스케줄로 넘어감
                }

                // 알림 유형과 주택구분에 따른 메시지 생성
                NotificationMessageDto notificationMessage = createNotificationMessage(schedule);

                // FCM 발송을 위한 메시지 객체 생성
                Message message = Message.builder()
                        .setToken(userToken)
                        // 알림 제목과 내용 설정
                        .setNotification(Notification.builder()
                                .setTitle(notificationMessage.getTitle())
                                .setBody(notificationMessage.getBody())
                                .build())
                        // 추가 데이터 설정 (앱에서 활용 가능)
                        .putData("type", schedule.getType().name())
                        .putData("pblancNo", schedule.getHousingAnnouncement().getPblancNo())
                        .putData("houseManageNo", schedule.getHousingAnnouncement().getHouseManageNo())
                        .build();

                // FCM 을 통해 실제 알림 발송
                String response = FirebaseMessaging.getInstance().send(message);
                log.info("Successfully sent notification: {}", response);

                // 발송된 알림 이력 저장
                saveNotificationHistory(schedule, notificationMessage);

                // 스케줄 상태를 발송완료로 변경
                schedule.markAsSent();

            } catch (FirebaseMessagingException e) {
                log.error("Failed to send FCM notification", e);
            }
        }
    }

    /**
     * 알림 메시지 생성 메서드
     * 주택구분코드와 알림 타입에 따라 적절한 메시지 생성
     */
    private NotificationMessageDto createNotificationMessage(NotificationSchedule schedule) {
        HousingAnnouncement announcement = schedule.getHousingAnnouncement();
        String houseNm = announcement.getHouseNm();
        String title = ""; // 초기화
        String body = null; // null 가능하므로 null 로 초기화

        // 주택구분코드에 따른 접두어 설정
        // 04: 무순위/잔여세대, 06: 계약취소주택, 11: 임의공급
        String prefix = switch (announcement.getHouseSecd()) {
            case "04" -> "무순위/잔여세대";
            case "06" -> "계약취소주택";
            case "11" -> "임의공급";
            default -> ""; // 01, 10의 경우 접두어 없음
        };

        // 알림 타입(시작전날/시작일/마감전날)에 따른 메시지 생성
        switch (schedule.getType()) {
            case START_TOMORROW -> title = String.format("%s %s 청약 신청일이 내일부터 시작입니다!", houseNm, prefix);

            case START_TODAY -> {
                title = String.format("%s %s 청약이 시작되었습니다!", houseNm, prefix);
                // 시작일과 마감일이 같으면 당일 신청 강조 메시지
                body = announcement.getRceptEndde().equals(announcement.getRceptBgnde()) ?
                        "오늘 하루만 신청 받으니 빨리 신청해보세요" :
                        "청약 상세 일정을 확인해보세요";
            }
            case END_TOMORROW -> {
                title = String.format("%s %s 청약 마감일 하루 전입니다!", houseNm, prefix);
                body = "청약 신청 놓치지 말아요";
            }
        }

        return new NotificationMessageDto(title, body);
    }

    /**
     * 발송된 알림 이력 저장 메서드
     * 추후 알림 목록 조회 등에 활용
     */
    private void saveNotificationHistory(NotificationSchedule schedule, NotificationMessageDto message) {
        // Entity 의 Notification 과 Firebase 의 Notification 이 충돌 => Entity 이름 NotificationHistory 로 변경
        NotificationHistory notification = NotificationHistory.builder()
                .user(schedule.getUser())
                .housingAnnouncement(schedule.getHousingAnnouncement())
                .notificationType(schedule.getType())
                .title(message.getTitle())
                .message(message.getBody())
                .createdAt(LocalDateTime.now())
                .build();

        notification = notificationHistoryRepository.save(notification);

        // SSE를 통해 실시간 알림도 전송
        sseEmitterService.sendToUser(
                notification.getUser().getUserId(),
                NotificationDto.from(notification)
        );
    }

    /**
     * 알림 읽음 처리
     */
    @Transactional
    public void markAsRead(Long notificationId, Long userId) {
        NotificationHistory notification = notificationHistoryRepository
                .findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        // 알림의 소유자 확인
        if (!notification.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("Not authorized to mark this notification as read");
        }

        notification.markAsRead();  // Entity 에 있는 markAsRead() 메서드 호출
    }

    // 테스트용 FCM 푸시 알림 전송
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
                            .setTitle("테스트 아파트 무순위/잔여세대 청약이 시작되었습니다!")
                            .setBody("오늘 하루만 신청 받으니 빨리 신청해보세요")
                            .build())
                    // 추가 데이터 (앱에서 활용 가능)
                    .putData("type", "TEST")
                    .putData("timestamp", String.valueOf(System.currentTimeMillis()))
                    .build();

            // FCM 으로 메시지 전송
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("Successfully sent test notification: {}", response);

        } catch (FirebaseMessagingException e) {
            throw new RuntimeException("Failed to send notification", e);
        }
    }
}
