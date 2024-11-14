package com.crunchit.housing_subscription.service;


import com.crunchit.housing_subscription.entity.HousingAnnouncement;
import com.crunchit.housing_subscription.entity.HousingAnnouncementModel;
import com.crunchit.housing_subscription.repository.HousingAnnouncementModelRepository;
import com.crunchit.housing_subscription.repository.HousingAnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HousingInsertService {
    private final HousingAnnouncementRepository announcementRepository;
    private final HousingAnnouncementModelRepository announcementModelRepository;
    @Transactional
    public void insertHousingAnnouncements(List<HousingAnnouncement> list){
        announcementRepository.saveAllAndFlush(list);
    }

    @Transactional
    public void insertHousingAnnouncementModel(List<HousingAnnouncementModel> list){
        announcementModelRepository.saveAllAndFlush(list);
    }
}
