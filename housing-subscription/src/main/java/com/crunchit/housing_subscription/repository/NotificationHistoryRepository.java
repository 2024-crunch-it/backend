package com.crunchit.housing_subscription.repository;

import com.crunchit.housing_subscription.entity.NotificationHistory;
import com.crunchit.housing_subscription.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationHistoryRepository extends JpaRepository<NotificationHistory, Long> {

    // 사용자별 알림 이력을 최신순으로 조회
    List<NotificationHistory> findByUserOrderByCreatedAtDesc(User user);
}
