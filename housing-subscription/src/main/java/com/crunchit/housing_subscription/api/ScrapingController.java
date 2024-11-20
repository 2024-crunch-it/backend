package com.crunchit.housing_subscription.api;


import com.crunchit.housing_subscription.entity.HousingAnnouncement;
import com.crunchit.housing_subscription.entity.HousingAnnouncementModel;
import com.crunchit.housing_subscription.service.HousingApiService;
import com.crunchit.housing_subscription.service.HousingInsertService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "apt 정보 스크래핑", description = "2024-01-01 부터 존재하는 모든 청약 공고를 스크래핑 하는 api입니다.")
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

    @Operation(summary = "무순위, 취소 정보 스크래핑", description = "2024-01-01 부터 존재하는 모든 무순위, 취소주택 청약 공고를 스크래핑 하는 api입니다.")
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


    @Operation(summary = "임의공급 정보 스크래핑", description = "2024-01-01 부터 존재하는 모든 임의 공급 청약 공고를 스크래핑 하는 api입니다.")
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
