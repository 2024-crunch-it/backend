package com.crunchit.housing_subscription.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class DepositResponseDto {
    private Long depositId;
    private int depositAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd")
    private LocalDateTime depositDate;
}