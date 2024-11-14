package com.crunchit.housing_subscription.repository;

import com.crunchit.housing_subscription.entity.HousingAnnouncement;
import com.crunchit.housing_subscription.entity.HousingAnnouncementId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HousingAnnouncementRepository extends JpaRepository<HousingAnnouncement, HousingAnnouncementId> {
}
