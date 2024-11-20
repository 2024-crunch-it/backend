package com.crunchit.housing_subscription.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String userName;
    private List<BadgeDto> ownedBadges;        // 보유한 뱃지 리스트
    private List<BadgeDto> unownedBadges;     // 보유하지 않은 뱃지 리스트
}