package com.crunchit.housing_subscription.repository;

import com.crunchit.housing_subscription.entity.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepositRepository extends JpaRepository<Deposit, Long> {
    // 특정 계좌의 입금 내역 조회
    List<Deposit> findAllByAccount_AccountId(Long accountId);
}
