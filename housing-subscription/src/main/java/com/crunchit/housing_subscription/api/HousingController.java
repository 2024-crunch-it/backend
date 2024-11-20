package com.crunchit.housing_subscription.api;


import com.crunchit.housing_subscription.dto.response.HousingDetailResponseDto;
import com.crunchit.housing_subscription.dto.response.HousingMappedResponseDto;
import com.crunchit.housing_subscription.dto.response.HousingMonthlyResponseDto;
import com.crunchit.housing_subscription.dto.response.HousingResponseDto;
import com.crunchit.housing_subscription.service.HousingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/housing")
public class HousingController {
    private final HousingService housingService;

    @Operation(summary = "청약공고 가져오는 기능 (List 형식)", description = "모든 청약공고를 가져오며, 공고날짜를 내림차순으로 정렬하여 보여줘요.")
    @GetMapping("/getAnnouncement")
    public ResponseEntity<?> getHousingAnnouncement(
            @Parameter(description = "페이지 번호, 페이지 크기, 유저 아이디")
            @RequestParam(name = "page") Integer page, @RequestParam(name = "pageSize") Integer pageSize, @RequestParam(name = "userId") Long userId) {
        HousingResponseDto housingAnnouncements = housingService.getHousingAnnouncements(page, pageSize, userId);
        return ResponseEntity.ok(housingAnnouncements);
    }

    @Operation(summary = "사용자가 찜한 청약공고 가져오는 기능 (List 형식)", description = "사용자가 찜한 모든 청약공고를 가져오며, 공고날짜를 내림차순으로 정렬하여 보여줘요.")
    @GetMapping("/getAnnouncementLike")
    public ResponseEntity<?> getHousingAnnouncementLike(
            @Parameter(description = "페이지 번호, 페이지 크기, 유저 아이디")
            @RequestParam(name = "page") Integer page, @RequestParam(name = "pageSize") Integer pageSize, @RequestParam(name = "userId") Long userId) {
        HousingResponseDto housingAnnouncements = housingService.getHousingAnnouncementsLike(page, pageSize, userId);
        return ResponseEntity.ok(housingAnnouncements);
    }

    @Operation(summary = "달력에 들어가는 청약공고 (월 별로)", description = "만족하는 년도, 월에 접수 진행 중인 모든 공고를 보여줘요.")
    @GetMapping("/getMonthlyAnnouncement")
    public ResponseEntity<?> getMonthlyHousingAnnouncement(
            @Parameter(description = "연도, 월, 유저 아이디")
            @RequestParam(name = "year") Integer year, @RequestParam(name = "month") Integer month, @RequestParam(name = "userId") Long userId) {
        HousingMonthlyResponseDto housingAnnouncements = housingService.getHousingMonthlyAnnouncements(year, month, userId);
        return ResponseEntity.ok(housingAnnouncements);
    }

    @Operation(summary = "달력에 들어가는 찜한 청약공고 (월 별로)", description = "만족하는 년도, 월에 접수 진행 중인 모든 찜한 공고를 보여줘요.")
    @GetMapping("/getMonthlyAnnouncementLike")
    public ResponseEntity<?> getMonthlyHousingAnnouncementLike(
            @Parameter(description = "연도, 월, 유저 아이디")
            @RequestParam(name = "year") Integer year, @RequestParam(name = "month") Integer month, @RequestParam(name = "userId") Long userId) {
        HousingMonthlyResponseDto housingAnnouncements = housingService.getHousingMonthlyAnnouncementsLike(year, month, userId);
        return ResponseEntity.ok(housingAnnouncements);
    }

    @Operation(summary = "지도에 들어가는 청약공고", description = "현재 마감되지 않은 청약 정보를 가져옵니다.")
    @GetMapping("/getMappedAnnouncement")
    public ResponseEntity<?> getMappedHousingAnnouncement(
            @Parameter(description = "유저 아이디")
            @RequestParam(name = "userId") Long userId) {
        HousingMappedResponseDto housingAnnouncements = housingService.getHousingMappedAnnouncements(userId);
        return ResponseEntity.ok(housingAnnouncements);
    }

    @Operation(summary = "지도에 들어가는 찜한 청약공고", description = "현재 마감되지 않은 청약 정보를 가져옵니다.")
    @GetMapping("/getMappedAnnouncementLike")
    public ResponseEntity<?> getMappedHousingAnnouncementLike(
            @Parameter(description = "유저 아이디")
            @RequestParam(name = "userId") Long userId) {

        HousingMappedResponseDto housingAnnouncements = housingService.getHousingMappedAnnouncementsLike(userId);
        return ResponseEntity.ok(housingAnnouncements);
    }

    @Operation(summary = "해당 공고의 자세한 정보", description = "각 공고의 자세한 정보, 모델 별 공급 수 등")
    @GetMapping("/getAnnouncementDetail")
    public ResponseEntity<?> getAnnouncementDetail(
            @Parameter(description = "공고 번호, 주택관리번호, 유저 아이디")
            @RequestParam(name = "pblancNo") String pblancNo, @RequestParam(name = "houseManageNo") String houseManageNo, @RequestParam(name = "userId") Long userId) {
        HousingDetailResponseDto announcementDetail = housingService.getHousingAnnouncementDetail(pblancNo, houseManageNo, userId);
        return ResponseEntity.ok(announcementDetail);
    }
}
