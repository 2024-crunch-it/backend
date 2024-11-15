package com.crunchit.housing_subscription.api;


import com.crunchit.housing_subscription.dto.response.HousingDetailResponseDto;
import com.crunchit.housing_subscription.dto.response.HousingResponseDto;
import com.crunchit.housing_subscription.service.HousingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/v1/housing")
public class HousingController {
    private final HousingService housingService;

    @GetMapping("/getAnnouncement")
    public ResponseEntity<?> getHousingAnnouncement(@RequestParam Integer page, @RequestParam Integer pageSize){
        HousingResponseDto housingAnnouncements = housingService.getHousingAnnouncements(page, pageSize);
        return ResponseEntity.ok(housingAnnouncements);
    }

    @GetMapping("/getAnnouncementDetail")
    public ResponseEntity<?> getAnnouncementDetail(@RequestParam String pblancNo, @RequestParam String houseManageNo){
        HousingDetailResponseDto announcementDetail = housingService.getHousingAnnouncementDetail(pblancNo, houseManageNo);
        return ResponseEntity.ok(announcementDetail);
    }
}
