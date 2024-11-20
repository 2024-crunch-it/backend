package com.crunchit.housing_subscription.dto.response;

import com.crunchit.housing_subscription.entity.NotificationHistory;
import com.crunchit.housing_subscription.entity.NotificationType;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private LocalDateTime createdAt;     // 알림 생성 시간
    @JsonProperty("isRead") // JSON 직렬화할 때 boolean 타입의 is를 없앰. 방지를 위해 추가
    private boolean isRead;              // 읽음 여부

    public static NotificationDto from(NotificationHistory notification) {
        return NotificationDto.builder()
                .notificationId(notification.getNotificationId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .type(notification.getNotificationType())
                .createdAt(notification.getCreatedAt())
                .isRead(notification.isRead())
                .build();
    }
}
