package com.crunchit.housing_subscription.repository;

import com.crunchit.housing_subscription.entity.UserBadge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {}