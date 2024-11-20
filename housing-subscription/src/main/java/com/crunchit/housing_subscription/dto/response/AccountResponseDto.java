package com.crunchit.housing_subscription.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountResponseDto {
    private Long accountId;
    private String accountNumber;
    private double balance;
}