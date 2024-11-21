package com.crunchit.housing_subscription.service;

import com.crunchit.housing_subscription.dto.response.AccountResponseDto;
import com.crunchit.housing_subscription.dto.response.DepositResponseDto;
import com.crunchit.housing_subscription.entity.Deposit;
import com.crunchit.housing_subscription.entity.User;
import com.crunchit.housing_subscription.repository.DepositRepository;
import com.crunchit.housing_subscription.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;
    private final DepositRepository depositRepository;

    @Transactional(readOnly = true)
    public List<AccountResponseDto> getAccountsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // User의 계좌 목록을 AccountResponseDto로 변환하여 반환
        return user.getAccounts().stream()
                .map(account -> new AccountResponseDto(
                        account.getAccountId(),
                        account.getAccountNumber(),
                        account.getBalance()
                ))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DepositResponseDto> getDepositsByAccountId(Long accountId) {
        List<Deposit> deposits = depositRepository.findAllByAccount_AccountIdOrderByDepositDateDesc(accountId);

        // 엔티티를 DTO로 변환
        return deposits.stream()
                .map(deposit -> new DepositResponseDto(
                        deposit.getDepositId(),
                        deposit.getDepositAmount(),
                        deposit.getDepositDate()
                ))
                .collect(Collectors.toList());
    }
}