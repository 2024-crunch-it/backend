package com.crunchit.housing_subscription.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotificationMessageDto { // 알림 메시지 Dto
    private String title;  // 알림 제목
    private String body;   // 알림 내용 (null 가능)
}
