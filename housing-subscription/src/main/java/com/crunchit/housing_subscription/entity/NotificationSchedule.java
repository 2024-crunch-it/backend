package com.crunchit.housing_subscription.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_notification_schedules")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationSchedule { // 알림 예약 스케줄을 관리하는 Entity

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id", nullable = false)
    private Long scheduleId;

    @ManyToOne(fetch = FetchType.LAZY) // LAZY : 필요한 데이터만 로딩할 수 있어 성능 최적화에 유리
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "pblanc_no", referencedColumnName = "pblanc_no", nullable = false),
            @JoinColumn(name = "house_manage_no", referencedColumnName = "house_manage_no", nullable = false)
    })
    private HousingAnnouncement housingAnnouncement;

    // 알림 예약 시간
    @Column(name = "scheduled_time", nullable = false)
    private LocalDateTime scheduledTime;

    // 알림 타입 (START_TOMORROW: 청약 시작 하루 전, START_TODAY: 청약 시작일, END_TOMORROW: 청약 마감 하루 전)
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(name = "is_sent", nullable = false)
    private boolean isSent = false; // 발송 여부

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 스케줄 생성 시간

    // 알림 발송 완료 처리
    public void markAsSent() {
        this.isSent = true;
    }

}
