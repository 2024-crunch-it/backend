package com.crunchit.housing_subscription.service;


import com.crunchit.housing_subscription.entity.HousingAnnouncement;
import com.crunchit.housing_subscription.entity.HousingAnnouncementModel;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HousingSchedulerService {
    private final HousingApiService apiService;
    private final HousingInsertService insertService;

    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Seoul")
    public void scheduledScrap(){
        String previousDate = getPreviousDate();
        List<HousingAnnouncement> announcements = apiService.getAptAnnouncements(previousDate);
        insertService.insertHousingAnnouncements(announcements);
        for(HousingAnnouncement announcement: announcements){
            String houseManageNo = announcement.getHouseManageNo();
            String pblancNo = announcement.getPblancNo();
            List<HousingAnnouncementModel> models = apiService.getAptModels(houseManageNo, pblancNo);
            insertService.insertHousingAnnouncementModel(models);
        }

        announcements = apiService.getRemainAnnouncements(previousDate);
        insertService.insertHousingAnnouncements(announcements);
        for(HousingAnnouncement announcement: announcements){
            String houseManageNo = announcement.getHouseManageNo();
            String pblancNo = announcement.getPblancNo();
            List<HousingAnnouncementModel> models = apiService.getRemainModels(houseManageNo, pblancNo);
            insertService.insertHousingAnnouncementModel(models);
        }

        announcements = apiService.getOptionAnnouncements(previousDate);
        insertService.insertHousingAnnouncements(announcements);
        for(HousingAnnouncement announcement: announcements){
            String houseManageNo = announcement.getHouseManageNo();
            String pblancNo = announcement.getPblancNo();
            List<HousingAnnouncementModel> models = apiService.getOptionModels(houseManageNo, pblancNo);
            insertService.insertHousingAnnouncementModel(models);
        }
    }


    private String getPreviousDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1); // 하루를 빼기
        Date yesterday = calendar.getTime();

        // 날짜 포맷 설정
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(yesterday);
    }
}
