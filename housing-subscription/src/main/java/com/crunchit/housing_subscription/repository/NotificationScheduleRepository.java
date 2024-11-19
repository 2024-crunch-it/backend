package com.crunchit.housing_subscription.repository;

import com.crunchit.housing_subscription.entity.HousingAnnouncement;
import com.crunchit.housing_subscription.entity.NotificationSchedule;
import com.crunchit.housing_subscription.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationScheduleRepository extends JpaRepository<NotificationSchedule, Long> {

    // 특정 사용자와 청약 공고에 대한 스케줄 삭제
    void deleteByUserAndHousingAnnouncement(User user, HousingAnnouncement announcement);

    // 발송 시간이 현재 이하이고 아직 발송되지 않은 스케줄 조회
    List<NotificationSchedule> findByScheduledTimeLessThanEqualAndIsSentFalse(LocalDateTime now);
}

