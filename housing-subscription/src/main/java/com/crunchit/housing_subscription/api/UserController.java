package com.crunchit.housing_subscription.api;

import com.crunchit.housing_subscription.dto.response.UserResponseDto;
import com.crunchit.housing_subscription.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "사용자 보유 뱃지 조회", description = "Response : {userId, userName, badge list}")
    @GetMapping("/badges")
    public UserResponseDto getUserWithBadges(@Parameter(description = "사용자 ID")
                                                 @RequestParam(name = "userId") Long userId) {
        return userService.getUserWithBadges(userId);
    }
}