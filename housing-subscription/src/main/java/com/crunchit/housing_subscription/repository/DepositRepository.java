package com.crunchit.housing_subscription.repository;

import com.crunchit.housing_subscription.entity.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepositRepository extends JpaRepository<Deposit, Long> {
    // 특정 Account ID에 속하는 Deposit 데이터를 depositDate 기준 내림차순 정렬
    List<Deposit> findAllByAccount_AccountIdOrderByDepositDateDesc(Long accountId);
}
