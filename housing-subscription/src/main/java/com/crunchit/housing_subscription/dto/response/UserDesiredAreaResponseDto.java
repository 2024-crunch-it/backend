package com.crunchit.housing_subscription.dto.response;

import com.crunchit.housing_subscription.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDesiredAreaResponseDto {
    private Long userId;
    private String userName;
    private int desiredArea;
    private String desiredAreaDescription;

    public static UserDesiredAreaResponseDto fromEntity(User user) {
        return new UserDesiredAreaResponseDto(
                user.getUserId(),
                user.getUserName(),
                user.getDesiredArea(),
                getDesiredAreaDescription(user.getDesiredArea())
        );
    }

    // desiredArea 값에 따라 설명을 반환하는 메서드
    private static String getDesiredAreaDescription(int desiredArea) {
        switch (desiredArea) {
            case 0:
                return "모든 면적";
            case 1:
                return "85제곱미터 이하";
            case 2:
                return "102제곱미터 이하";
            case 3:
                return "135제곱미터 이하";
            default:
                throw new IllegalArgumentException("유효하지 않은 desired_area 값입니다: " + desiredArea);
        }
    }
}
