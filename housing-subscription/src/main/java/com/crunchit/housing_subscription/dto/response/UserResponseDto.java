package com.crunchit.housing_subscription.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String userName;
    private List<BadgeDto> badges;
}