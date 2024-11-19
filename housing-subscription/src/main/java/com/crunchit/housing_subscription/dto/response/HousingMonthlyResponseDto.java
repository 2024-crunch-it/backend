package com.crunchit.housing_subscription.dto.response;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HousingMonthlyResponseDto {
    private Integer year;
    private Integer month;
    private Integer totalItems;
    private List<HousingMonthlyDto> data;
}
