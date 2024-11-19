package com.crunchit.housing_subscription.entity;

import lombok.Getter;

@Getter
public enum NotificationType { // 알림 타입
    START_TOMORROW("청약 시작 하루 전 알림"),
    START_TODAY("청약 시작일 알림"),
    END_TOMORROW("청약 마감 하루 전 알림"),
    BADGE("뱃지 획득 알림");

    private final String description;

    NotificationType(String description) {
        this.description = description;
    }
}
