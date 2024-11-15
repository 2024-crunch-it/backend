package com.crunchit.housing_subscription.dto.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HousingDetailResponseDto {
    private HousingAnnouncementDto data;
}
