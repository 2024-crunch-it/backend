package com.crunchit.housing_subscription.api;

import com.crunchit.housing_subscription.dto.response.UserResponseDto;
import com.crunchit.housing_subscription.service.BadgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final BadgeService badgeService;

    @Operation(summary = "사용자 보유 뱃지 조회", description = "사용자의 아이디와 이름 그리고 보유하고 있는 배지 종류를 반환")
    @GetMapping("/badges")
    public UserResponseDto getUserWithBadges(@Parameter(description = "사용자 ID")
                                                 @RequestParam(name = "userId") Long userId) {
        return badgeService.getUserWithBadges(userId);
    }

    @Operation(summary = "페이지 방문 수 증가", description = "사용자의 페이지 방문 횟수를 1 증가시키고 조건 충족 시 뱃지 지급")
    @PostMapping("/visit")
    public void incrementPageVisit(
            @Parameter(description = "사용자 ID")
            @RequestParam(name = "userId") Long userId) {
        badgeService.incrementPageVisit(userId);
    }


    @Operation(summary = "청약 납입", description = "사용자의 청약 납입 횟수를 1 증가시키고, 금액 납입, 조건 충족 시 뱃지 지급")
    @PostMapping("/deposit")
    public void incrementDeposit(
            @Parameter(description = "사용자 ID, 계좌 ID, 납입 금액")
            @RequestParam(name = "userId") Long userId, Long accountId, int depositAmount) {
        badgeService.incrementDeposit(userId, accountId, depositAmount);
    }

//
//    @Operation(summary = "청약 정보 찜 수 증가", description = "사용자의 청약 정보 찜 횟수를 1 증가시키고 조건 충족 시 뱃지 지급")
//    @PostMapping("/bookmark")
//    public void incrementBookmark(
//            @Parameter(description = "사용자 ID")
//            @RequestParam(name = "userId") Long userId) {
//        badgeService.incrementBookmark(userId);
//    }
}