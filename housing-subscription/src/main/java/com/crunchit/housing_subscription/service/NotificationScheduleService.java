package com.crunchit.housing_subscription.service;

import com.crunchit.housing_subscription.entity.HousingAnnouncement;
import com.crunchit.housing_subscription.entity.NotificationSchedule;
import com.crunchit.housing_subscription.entity.NotificationType;
import com.crunchit.housing_subscription.entity.User;
import com.crunchit.housing_subscription.repository.NotificationScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationScheduleService {
    private final NotificationScheduleRepository notificationScheduleRepository;

    // 청약 좋아요 시 알림 스케줄 생성
    public void createNotificationSchedules(User user, HousingAnnouncement announcement) {
        LocalDate startDate, endDate;

        // 주택구분코드에 따른 날짜 설정
        if (Arrays.asList("01", "10").contains(announcement.getHouseSecd())) {
            startDate = announcement.getRceptBgnde().toInstant()
                    .atZone(ZoneId.of("Asia/Seoul"))
                    .toLocalDate();
            endDate = announcement.getRceptEndde().toInstant()
                    .atZone(ZoneId.of("Asia/Seoul"))
                    .toLocalDate();
        } else {
            startDate = announcement.getSubscrptRceptBgnde().toInstant()
                    .atZone(ZoneId.of("Asia/Seoul"))
                    .toLocalDate();
            endDate = announcement.getSubscrptRceptEndde().toInstant()
                    .atZone(ZoneId.of("Asia/Seoul"))
                    .toLocalDate();
        }

        // 이미 존재하는 스케줄 확인 및 삭제 (중복 방지)
        deleteExistingSchedules(user, announcement);

        // 시작일 하루 전 알림
        createSchedule(user, announcement,
                startDate.minusDays(1).atTime(9, 0),
                NotificationType.START_TOMORROW);

        // 시작일 당일 알림
        createSchedule(user, announcement,
                startDate.atTime(9, 0),
                NotificationType.START_TODAY);

        // 마감일 전날 알림 (시작일과 마감일이 다른 경우에만)
        if (!startDate.equals(endDate)) {
            createSchedule(user, announcement,
                    endDate.minusDays(1).atTime(9, 0),
                    NotificationType.END_TOMORROW);
        }
    }

    // public 메소드 추가
    public void deleteNotificationSchedules(User user, HousingAnnouncement announcement) {
        deleteExistingSchedules(user, announcement);
    }

    // 기존 스케줄 삭제
    private void deleteExistingSchedules(User user, HousingAnnouncement announcement) {
        notificationScheduleRepository.deleteByUserAndHousingAnnouncement(user, announcement);
    }

    // 스케줄 생성
    private void createSchedule(User user, HousingAnnouncement announcement,
                                LocalDateTime scheduledTime, NotificationType type) {
        // 현재 시간 이후의 스케줄만 생성
        if (scheduledTime.isAfter(LocalDateTime.now())) {
            NotificationSchedule schedule = NotificationSchedule.builder()
                    .user(user)
                    .housingAnnouncement(announcement)
                    .scheduledTime(scheduledTime)
                    .type(type)
                    .build();

            notificationScheduleRepository.save(schedule);
            log.info("Created notification schedule: userId={}, announcementId={}, type={}, scheduledTime={}",
                    user.getUserId(), announcement.getPblancNo(), type, scheduledTime);
        }
    }

    // 발송할 알림 스케줄 조회 (스케줄러에서 사용)
    public List<NotificationSchedule> findSchedulesToSend() {
        return notificationScheduleRepository.findByScheduledTimeLessThanEqualAndIsSentFalse(
                LocalDateTime.now()
        );
    }
}
