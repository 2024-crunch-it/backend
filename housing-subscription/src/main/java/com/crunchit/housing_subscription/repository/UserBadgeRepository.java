package com.crunchit.housing_subscription.repository;

import com.crunchit.housing_subscription.entity.Badge;
import com.crunchit.housing_subscription.entity.User;
import com.crunchit.housing_subscription.entity.UserBadge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {
    // 특정 사용자와 뱃지 매핑 여부 확인
    boolean existsByUserAndBadge(User user, Badge badge);
}
