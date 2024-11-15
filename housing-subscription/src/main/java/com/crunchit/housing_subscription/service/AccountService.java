package com.crunchit.housing_subscription.service;

import com.crunchit.housing_subscription.dto.response.AccountResponseDto;
import com.crunchit.housing_subscription.entity.User;
import com.crunchit.housing_subscription.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;

    public List<AccountResponseDto> getAccountsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // User의 계좌 목록을 AccountResponseDto로 변환하여 반환
        return user.getAccounts().stream()
                .map(account -> new AccountResponseDto(
                        account.getAccountId(),
                        account.getAccountNumber(),
                        account.getBalance(),
                        account.getDepositCount()
                ))
                .collect(Collectors.toList());
    }
}