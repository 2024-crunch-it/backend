package com.crunchit.housing_subscription.repository;

import com.crunchit.housing_subscription.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"userBadges.badge"})
    Optional<User> findWithBadgesByUserId(Long userId);
}