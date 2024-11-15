package com.crunchit.housing_subscription.service;

import com.crunchit.housing_subscription.dto.externalApi.response.ApiAnnouncementResponseDto;
import com.crunchit.housing_subscription.dto.externalApi.response.ApiModelResponseDto;
import com.crunchit.housing_subscription.dto.externalApi.response.HousingAnnouncementApiDto;
import com.crunchit.housing_subscription.dto.externalApi.response.HousingModelApiDto;
import com.crunchit.housing_subscription.entity.HousingAnnouncement;
import com.crunchit.housing_subscription.entity.HousingAnnouncementModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HousingApiService {
    private final WebClient.Builder webClientBuilder;
    static final int PAGE_SIZE = 100;

    @Value("${spring.api.secret}")
    private String secret;
    public List<HousingAnnouncement> getAptAnnouncements(String date) {
        String baseUrl = "https://api.odcloud.kr/api/ApplyhomeInfoDetailSvc/v1/getAPTLttotPblancDetail";

        return getHousingAnnouncements(baseUrl, date);
    }


    public List<HousingAnnouncement> getRemainAnnouncements(String date) { //무순위, 잔여세대
        String baseUrl = "https://api.odcloud.kr/api/ApplyhomeInfoDetailSvc/v1/getRemndrLttotPblancDetail";

        return getHousingAnnouncements(baseUrl, date);
    }

    public List<HousingAnnouncement> getOptionAnnouncements(String date) { //임의 공급
        String baseUrl = "https://api.odcloud.kr/api/ApplyhomeInfoDetailSvc/v1/getOPTLttotPblancDetail";

        return getHousingAnnouncements(baseUrl, date);
    }
    public List<HousingAnnouncementModel> getAptModels(String houseManageNo, String pblancNo){
        String baseUrl = "https://api.odcloud.kr/api/ApplyhomeInfoDetailSvc/v1/getAPTLttotPblancMdl";
        return getHousingAnnouncementModels(houseManageNo, pblancNo, baseUrl);
    }

    public List<HousingAnnouncementModel> getRemainModels(String houseManageNo, String pblancNo){
        String baseUrl = "https://api.odcloud.kr/api/ApplyhomeInfoDetailSvc/v1/getRemndrLttotPblancMdl";
        return getHousingAnnouncementModels(houseManageNo, pblancNo, baseUrl);
    }

    public List<HousingAnnouncementModel> getOptionModels(String houseManageNo, String pblancNo){
        String baseUrl = "https://api.odcloud.kr/api/ApplyhomeInfoDetailSvc/v1/getOPTLttotPblancMdl";
        return getHousingAnnouncementModels(houseManageNo, pblancNo, baseUrl);
    }

    private List<HousingAnnouncementModel> getHousingAnnouncementModels(String houseManageNo, String pblancNo, String baseUrl) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        WebClient webClient = webClientBuilder.uriBuilderFactory(factory).build();
        List<HousingAnnouncementModel> housingModels = new ArrayList<>();

        int currentPage = 1;
        int totalPages;

        do {
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                    .queryParam("page", currentPage)
                    .queryParam("perPage", PAGE_SIZE)
                    .queryParam("serviceKey", secret)
                    .queryParam("cond%5BHOUSE_MANAGE_NO%3A%3AEQ%5D", houseManageNo)
                    .queryParam("cond%5BPBLANC_NO%3A%3AEQ%5D", pblancNo)
                    .build(false)
                    .toString();

            ApiModelResponseDto responseDto = webClient
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(ApiModelResponseDto.class)
                    .block();

            assert responseDto != null;

            int matchCount = responseDto.getMatchCount();
            totalPages = (matchCount / PAGE_SIZE) + 1;


            for (HousingModelApiDto dto : responseDto.getData()) {
                housingModels.add(dto.toEntity());
            }
            currentPage++;

        } while (currentPage <= totalPages);

        return housingModels;
    }

    private List<HousingAnnouncement>  getHousingAnnouncements(String baseUrl, String date) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        WebClient webClient = webClientBuilder.uriBuilderFactory(factory).build();
        List<HousingAnnouncement> housingAnnouncements = new ArrayList<>();

        int currentPage = 1;
        int totalPages;

        do {
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                    .queryParam("page", currentPage)
                    .queryParam("perPage", PAGE_SIZE)
                    .queryParam("serviceKey", secret)
                    .queryParam("cond%5BRCRIT_PBLANC_DE%3A%3AGTE%5D", date)
                    .build(false)
                    .toString();

            ApiAnnouncementResponseDto responseDto = webClient
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(ApiAnnouncementResponseDto.class)
                    .block();

            assert responseDto != null;

            int matchCount = responseDto.getMatchCount();
            totalPages = (matchCount / PAGE_SIZE) + 1;


            for (HousingAnnouncementApiDto dto : responseDto.getData()) {
                housingAnnouncements.add(dto.toEntity());
            }
            currentPage++;

        } while (currentPage <= totalPages);

        return housingAnnouncements;
    }

}

