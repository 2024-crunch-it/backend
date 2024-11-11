package com.crunchit.housing_subscription.dto.response;

import com.crunchit.housing_subscription.entity.Test;
import lombok.Getter;

@Getter
public class TestResponseDto {
    private Long id;
    private String name;
    private String description;

    public TestResponseDto(Test test) {
        this.id = test.getId();
        this.name = test.getName();
        this.description = test.getDescription();
    }
}
