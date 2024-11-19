package com.crunchit.housing_subscription.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FcmTokenRequestDto { // FCM 토큰 요청 DTO
    private String token;
}
