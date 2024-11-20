package com.crunchit.housing_subscription.api;

import com.crunchit.housing_subscription.dto.response.DepositResponseDto;
import com.crunchit.housing_subscription.service.AccountService;
import com.crunchit.housing_subscription.service.BadgeService;
import com.crunchit.housing_subscription.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;
    private final BadgeService badgeService;

    @Operation(summary = "사용자 보유 계좌 조회", description = "사용자 보유 모든 계좌 반환")
    @GetMapping("accounts")
    public ResponseEntity<?> getAccountsByUserId(@Parameter(description = "사용자 ID")
                                                 @RequestParam(name = "userId") Long userId) {
        return new ResponseEntity<>(accountService.getAccountsByUserId(userId), HttpStatus.OK);
    }

    @Operation(summary = "청약 납입", description = "사용자의 청약 납입 횟수를 1 증가시키고, 금액 납입, 조건 충족 시 뱃지 지급")
    @PostMapping("/deposit")
    public ResponseEntity<?> incrementDeposit(
            @Parameter(description = "사용자 ID, 계좌 ID, 납입 금액")
            @RequestParam(name = "userId") Long userId, Long accountId, int depositAmount) {
        badgeService.incrementDeposit(userId, accountId, depositAmount);
        return new ResponseEntity<>("deposit ok", HttpStatus.OK);
    }

    @GetMapping("/accounts-balance-total")
    @Operation(summary = "사용자의 계좌 잔액 총합 조회", description = "특정 사용자의 모든 계좌 잔액 총합을 반환")
    public ResponseEntity<?> getTotalAccountBalance(@Parameter(description = "사용자 ID")
                                                    @RequestParam Long userId) {
        double totalBalance = userService.getTotalAccountBalance(userId);
        return new ResponseEntity<>(totalBalance, HttpStatus.OK);
    }

    @Operation(summary = "청약 납입 리스트", description = "사용자의 특정 계좌 청약 납입 기록 조회")
    @GetMapping("/deposits")
    public ResponseEntity<List<DepositResponseDto>> getDepositsByAccountId(@Parameter(description = "계좌 ID")
                                                                            @RequestParam Long accountId) {
        List<DepositResponseDto> depositList = accountService.getDepositsByAccountId(accountId);
        return ResponseEntity.ok(depositList);
    }


}
