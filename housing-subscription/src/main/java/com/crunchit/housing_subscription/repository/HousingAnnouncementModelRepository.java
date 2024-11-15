package com.crunchit.housing_subscription.repository;

import com.crunchit.housing_subscription.entity.HousingAnnouncementModel;
import com.crunchit.housing_subscription.entity.HousingAnnouncementModelId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HousingAnnouncementModelRepository extends JpaRepository<HousingAnnouncementModel, HousingAnnouncementModelId> {
}
