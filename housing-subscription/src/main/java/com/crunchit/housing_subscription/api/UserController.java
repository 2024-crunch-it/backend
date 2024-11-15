package com.crunchit.housing_subscription.api;

import com.crunchit.housing_subscription.dto.response.UserResponseDto;
import com.crunchit.housing_subscription.service.AccountService;
import com.crunchit.housing_subscription.service.BadgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final BadgeService badgeService;
    private final AccountService accountService;

    @Operation(summary = "사용자 보유 계좌 조회", description = "사용자 보유 모든 계좌 반환")
    @GetMapping("accounts")
    public ResponseEntity<?> getAccountsByUserId(@Parameter(description = "사용자 ID")
                                                                @RequestParam Long userId) {
        return new ResponseEntity<>(accountService.getAccountsByUserId(userId), HttpStatus.OK);
    }

    @Operation(summary = "사용자 보유 뱃지 조회", description = "사용자의 아이디와 이름 그리고 보유하고 있는 배지 종류를 반환")
    @GetMapping("/badges")
    public ResponseEntity<?> getUserWithBadges(@Parameter(description = "사용자 ID")
                                                 @RequestParam(name = "userId") Long userId) {
        return new ResponseEntity<>(badgeService.getUserWithBadges(userId), HttpStatus.OK);
    }

    @Operation(summary = "페이지 방문 수 증가", description = "사용자의 페이지 방문 횟수를 1 증가시키고 조건 충족 시 뱃지 지급")
    @PostMapping("/visit")
    public ResponseEntity<?> incrementPageVisit(
            @Parameter(description = "사용자 ID")
            @RequestParam(name = "userId") Long userId) {
        badgeService.incrementPageVisit(userId);
        return new ResponseEntity<>("page visit ok", HttpStatus.OK);
    }


    @Operation(summary = "청약 납입", description = "사용자의 청약 납입 횟수를 1 증가시키고, 금액 납입, 조건 충족 시 뱃지 지급")
    @PostMapping("/deposit")
    public ResponseEntity<?> incrementDeposit(
            @Parameter(description = "사용자 ID, 계좌 ID, 납입 금액")
            @RequestParam(name = "userId") Long userId, Long accountId, int depositAmount) {
        badgeService.incrementDeposit(userId, accountId, depositAmount);
        return new ResponseEntity<>("deposit ok", HttpStatus.OK);
    }

//
//    @Operation(summary = "청약 정보 찜 수 증가", description = "사용자의 청약 정보 찜 횟수를 1 증가시키고 조건 충족 시 뱃지 지급")
//    @PostMapping("/bookmark")
//    public ResponseEntity<?> incrementBookmark(
//            @Parameter(description = "사용자 ID")
//            @RequestParam(name = "userId") Long userId) {
//    return new ResponseEntity<>("test ok", HttpStatus.OK);
//        badgeService.incrementBookmark(userId);
//    }
}