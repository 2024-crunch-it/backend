package com.crunchit.housing_subscription.dto.request;

import com.crunchit.housing_subscription.entity.Test;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TestRequestDto {
    private String name;
    private String description;

    public Test toEntity() {
        return new Test(name, description);
    }
}