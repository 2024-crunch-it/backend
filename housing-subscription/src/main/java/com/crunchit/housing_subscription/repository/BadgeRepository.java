package com.crunchit.housing_subscription.repository;

import com.crunchit.housing_subscription.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long> {}