package com.crunchit.housing_subscription.dto.response;

import com.crunchit.housing_subscription.dto.response.HousingListDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HousingResponseDto {
    private Integer page;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalItems;
    private List<HousingListDto> data;
}
