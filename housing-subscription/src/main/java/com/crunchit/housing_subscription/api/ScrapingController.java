package com.crunchit.housing_subscription.api;


import com.crunchit.housing_subscription.service.HousingApiService;
import com.crunchit.housing_subscription.service.HousingInsertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("api/v1/scrap")
public class ScrapingController {
    private final HousingApiService apiService;
    private final HousingInsertService insertService;

    @GetMapping("/scrapTest")
    public ResponseEntity<?> scrapApi() {
        insertService.insertHousingAnnouncements(apiService.getAptAnnouncements("2024-01-01"));
        insertService.insertHousingAnnouncements(apiService.getRemainAnnouncements("2024-01-01"));
        insertService.insertHousingAnnouncements(apiService.getOptionAnnouncements("2024-01-01"));

        return new ResponseEntity<>("test ok", HttpStatus.OK);
    }
}
