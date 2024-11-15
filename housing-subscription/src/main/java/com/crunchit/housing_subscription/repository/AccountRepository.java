package com.crunchit.housing_subscription.repository;

import com.crunchit.housing_subscription.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}