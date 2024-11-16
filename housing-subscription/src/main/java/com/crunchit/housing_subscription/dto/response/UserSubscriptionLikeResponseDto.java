package com.crunchit.housing_subscription.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserSubscriptionLikeResponseDto {
    private Long userId;
    private String userName;
    private String pblancNo;
    private String houseManageNo;
    private LocalDateTime likedAt;
}