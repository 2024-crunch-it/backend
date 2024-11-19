package com.crunchit.housing_subscription.dto.response;

import com.crunchit.housing_subscription.entity.NotificationHistory;
import com.crunchit.housing_subscription.entity.NotificationType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NotificationDto { // 알림 조회 응답 Dto
    private Long notificationId;         // 알림 ID
    private String title;                // 알림 제목
    private String message;              // 알림 내용
    private NotificationType type;       // 알림 타입
    private String pblancNo;             // 공고번호
    private String houseManageNo;        // 주택관리번호
    private LocalDateTime createdAt;     // 알림 생성 시간
    private boolean isRead;              // 읽음 여부

    public static NotificationDto from(NotificationHistory notification) {
        return NotificationDto.builder()
                .notificationId(notification.getNotificationId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .type(notification.getNotificationType())
                .pblancNo(notification.getHousingAnnouncement().getPblancNo())
                .houseManageNo(notification.getHousingAnnouncement().getHouseManageNo())
                .createdAt(notification.getCreatedAt())
                .isRead(notification.isRead())
                .build();
    }
}
