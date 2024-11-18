package com.crunchit.housing_subscription.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_notifications")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationHistory { // 알림 이력을 저장하는 Entity

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", nullable = false)
    private Long notificationId;

    @ManyToOne(fetch = FetchType.LAZY) // LAZY : 필요한 데이터만 로딩할 수 있어 성능 최적화에 유리
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "pblanc_no", referencedColumnName = "pblanc_no", nullable = false),
            @JoinColumn(name = "house_manage_no", referencedColumnName = "house_manage_no", nullable = false)
    })
    private HousingAnnouncement housingAnnouncement;

    // 알림 타입 (START_TOMORROW: 청약 시작 하루 전, START_TODAY: 청약 시작일, END_TOMORROW: 청약 마감 하루 전)
    @Column(name = "notification_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Column(name = "title", nullable = false) // 알림 제목 (예: "XX 아파트 청약이 시작되었습니다!")
    private String title;

    @Column(name = "message") // 알림 내용 (예: "청약 상세 일정을 확인해보세요")
    private String message;

    @Column(name = "is_read", nullable = false)
    private boolean isRead = false; // 알림 읽음 여부

    @CreationTimestamp // Hibernate 가 엔티티를 저장하는 시점에 자동으로 현재 시간을 설정
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 알림 생성 시간

    @Column(name = "read_at")
    private LocalDateTime readAt; // 알림 읽은 시간

    // 알림 읽음 처리
    public void markAsRead() {
        this.isRead = true;
        this.readAt = LocalDateTime.now();
    }

}
