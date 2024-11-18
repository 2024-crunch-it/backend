package com.crunchit.housing_subscription.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HousingMappedResponseDto {
    private Integer totalItems;
    private List<HousingMappedDto> data;
}
