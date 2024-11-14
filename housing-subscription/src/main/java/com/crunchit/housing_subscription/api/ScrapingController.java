package com.crunchit.housing_subscription.api;


import com.crunchit.housing_subscription.entity.HousingAnnouncement;
import com.crunchit.housing_subscription.entity.HousingAnnouncementModel;
import com.crunchit.housing_subscription.service.HousingApiService;
import com.crunchit.housing_subscription.service.HousingInsertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("api/v1/scrap")
public class ScrapingController {
    private final HousingApiService apiService;
    private final HousingInsertService insertService;

    @GetMapping("/scrapApt") // 기본
    public ResponseEntity<?> scrapAptAnnouncement() {
        List<HousingAnnouncement> announcements = apiService.getAptAnnouncements("2024-01-01");
        insertService.insertHousingAnnouncements(announcements);
        for(HousingAnnouncement announcement: announcements){
            String houseManageNo = announcement.getHouseManageNo();
            String pblancNo = announcement.getPblancNo();
            List<HousingAnnouncementModel> models = apiService.getAptModels(houseManageNo, pblancNo);
            insertService.insertHousingAnnouncementModel(models);
        }
        return new ResponseEntity<>("test ok", HttpStatus.OK);
    }

    @GetMapping("/scrapRemain") //무순위, 취소
    public ResponseEntity<?> scrapRemainAnnouncement() {
        List<HousingAnnouncement> announcements = apiService.getRemainAnnouncements("2024-01-01");
        insertService.insertHousingAnnouncements(announcements);
        for(HousingAnnouncement announcement: announcements){
            String houseManageNo = announcement.getHouseManageNo();
            String pblancNo = announcement.getPblancNo();
            List<HousingAnnouncementModel> models = apiService.getRemainModels(houseManageNo, pblancNo);
            insertService.insertHousingAnnouncementModel(models);
        }
        return new ResponseEntity<>("test ok", HttpStatus.OK);
    }

    @GetMapping("/scrapOption") //임의 공급
    public ResponseEntity<?> scrapOptionAnnouncement() {
        List<HousingAnnouncement> announcements = apiService.getOptionAnnouncements("2024-01-01");
        insertService.insertHousingAnnouncements(announcements);
        for(HousingAnnouncement announcement: announcements){
            String houseManageNo = announcement.getHouseManageNo();
            String pblancNo = announcement.getPblancNo();
            List<HousingAnnouncementModel> models = apiService.getOptionModels(houseManageNo, pblancNo);
            insertService.insertHousingAnnouncementModel(models);
        }
        return new ResponseEntity<>("test ok", HttpStatus.OK);
    }
}
