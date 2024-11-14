package com.crunchit.housing_subscription.dto.externalApi.response;

import lombok.Data;

import java.util.List;

@Data
public class ApiModelResponseDto {
    private Integer currentCount;
    private List<HousingModelDto> data;
    private Integer matchCount;
    private Integer page;
    private Integer perPage;
    private Integer totalCount;
}
