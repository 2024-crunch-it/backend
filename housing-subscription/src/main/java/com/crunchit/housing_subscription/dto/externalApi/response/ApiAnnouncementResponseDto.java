package com.crunchit.housing_subscription.dto.externalApi.response;

import lombok.Data;

import java.util.List;

@Data
public class ApiAnnouncementResponseDto {
    private Integer currentCount;
    private List<HousingAnnouncementDto> data;
    private Integer matchCount;
    private Integer page;
    private Integer perPage;
    private Integer totalCount;
}
