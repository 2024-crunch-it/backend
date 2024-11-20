package com.crunchit.housing_subscription.api;


import com.crunchit.housing_subscription.dto.response.HousingDetailResponseDto;
import com.crunchit.housing_subscription.dto.response.HousingMappedResponseDto;
import com.crunchit.housing_subscription.dto.response.HousingMonthlyResponseDto;
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
    public ResponseEntity<?> getHousingAnnouncement(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam Long userId){
        HousingResponseDto housingAnnouncements = housingService.getHousingAnnouncements(page, pageSize, userId);
        return ResponseEntity.ok(housingAnnouncements);
    }

    @GetMapping("/getAnnouncementLike")
    public ResponseEntity<?> getHousingAnnouncementLike(@RequestParam Integer page, @RequestParam Integer pageSize, @RequestParam Long userId){
        HousingResponseDto housingAnnouncements = housingService.getHousingAnnouncementsLike(page, pageSize, userId);
        return ResponseEntity.ok(housingAnnouncements);
    }

    @GetMapping("/getMonthlyAnnouncement")
    public ResponseEntity<?> getMonthlyHousingAnnouncement(@RequestParam Integer year, @RequestParam Integer month, @RequestParam Long userId) {
        HousingMonthlyResponseDto housingAnnouncements = housingService.getHousingMonthlyAnnouncements(year, month, userId);
        return ResponseEntity.ok(housingAnnouncements);
    }

    @GetMapping("/getMonthlyAnnouncementLike")
    public ResponseEntity<?> getMonthlyHousingAnnouncementLike(@RequestParam Integer year, @RequestParam Integer month, @RequestParam Long userId) {
        HousingMonthlyResponseDto housingAnnouncements = housingService.getHousingMonthlyAnnouncementsLike(year, month, userId);
        return ResponseEntity.ok(housingAnnouncements);
    }

    @GetMapping("/getMappedAnnouncement")
    public ResponseEntity<?> getMappedHousingAnnouncement(@RequestParam(name = "userId") Long userId) {
        HousingMappedResponseDto housingAnnouncements = housingService.getHousingMappedAnnouncements(userId);
        return ResponseEntity.ok(housingAnnouncements);
    }

    @GetMapping("/getMappedAnnouncementLike")
    public ResponseEntity<?> getMappedHousingAnnouncementLike(@RequestParam(name = "userId") Long userId){
        HousingMappedResponseDto housingAnnouncements = housingService.getHousingMappedAnnouncementsLike(userId);
        return ResponseEntity.ok(housingAnnouncements);
    }

    @GetMapping("/getAnnouncementDetail")
    public ResponseEntity<?> getAnnouncementDetail(@RequestParam String pblancNo, @RequestParam String houseManageNo, @RequestParam Long userId){
        HousingDetailResponseDto announcementDetail = housingService.getHousingAnnouncementDetail(pblancNo, houseManageNo, userId);
        return ResponseEntity.ok(announcementDetail);
    }
}
