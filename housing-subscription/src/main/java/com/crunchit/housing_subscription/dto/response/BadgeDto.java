package com.crunchit.housing_subscription.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BadgeDto {
    private int badgeNumber;
    private String badgeName;
}