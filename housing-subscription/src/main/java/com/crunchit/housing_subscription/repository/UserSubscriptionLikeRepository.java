package com.crunchit.housing_subscription.repository;

import com.crunchit.housing_subscription.entity.HousingAnnouncement;
import com.crunchit.housing_subscription.entity.HousingAnnouncementId;
import com.crunchit.housing_subscription.entity.User;
import com.crunchit.housing_subscription.entity.UserSubscriptionLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSubscriptionLikeRepository extends JpaRepository<UserSubscriptionLike, Long> {

    /**
     * 특정 사용자와 특정 청약 공고에 대한 찜 여부 확인
     */
    boolean existsByUserAndHousingAnnouncement(User user, HousingAnnouncement housingAnnouncement);

    /**
     * 특정 사용자와 특정 청약 공고에 대한 찜 정보 조회
     */
    Optional<UserSubscriptionLike> findByUserAndHousingAnnouncement(User user, HousingAnnouncement housingAnnouncement);

    /**
     * 특정 사용자의 찜 목록 조회
     */
    List<UserSubscriptionLike> findAllByUser_UserId(Long userId);

}