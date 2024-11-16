package com.crunchit.housing_subscription.service;

import com.crunchit.housing_subscription.dto.response.BadgeDto;
import com.crunchit.housing_subscription.dto.response.UserResponseDto;
import com.crunchit.housing_subscription.entity.Account;
import com.crunchit.housing_subscription.entity.Badge;
import com.crunchit.housing_subscription.entity.User;
import com.crunchit.housing_subscription.repository.AccountRepository;
import com.crunchit.housing_subscription.repository.BadgeRepository;
import com.crunchit.housing_subscription.repository.UserBadgeRepository;
import com.crunchit.housing_subscription.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BadgeService {

    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;
    private final AccountRepository accountRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional(readOnly = true)
    public UserResponseDto getUserWithBadges(Long userId) {
        User user = userRepository.findWithBadgesByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<BadgeDto> badgeDtos = user.getUserBadges().stream()
                .map(userBadge -> new BadgeDto(
                        userBadge.getBadge().getBadgeNumber(),
                        userBadge.getBadge().getBadgeName()
                ))
                .collect(Collectors.toList());

        return new UserResponseDto(user.getUserId(), user.getUserName(), badgeDtos);
    }

    // 주택청약 페이지 방문 시 호출되는 메서드
    public void incrementPageVisit(Long userId) {
        String eventKey = "user:" + userId + ":pageVisit";
        String dbCountKey = "user:" + userId + ":dbPageVisit";

        ValueOperations<String, Object> operations = redisTemplate.opsForValue();

        // Redis에서 방문 횟수 증가
        Integer redisVisitCount = Math.toIntExact(operations.increment(eventKey, 1)); // Long -> Integer 변환

        // dbCountKey 값이 없다면 DB에서 조회하여 초기화
        Integer dbVisitCount = (Integer) operations.get(dbCountKey);
        if (dbVisitCount == null) {
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            dbVisitCount = user.getPageVisitCount();
            operations.set(dbCountKey, dbVisitCount, 1, TimeUnit.DAYS); // 하루 동안 유지
        }
        // DB와 Redis의 카운트를 합산하여 조건 체크
        int totalVisitCount = Optional.ofNullable(dbVisitCount).orElse(0) + redisVisitCount;
        checkAndAssignBadge(userId, totalVisitCount, "pageVisit");


//         조건 만족 시 DB와 Redis의 dbCountKey를 업데이트
        if (totalVisitCount == 1 || totalVisitCount == 10 || totalVisitCount == 50 || totalVisitCount == 100) {
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            user.setPageVisitCount(totalVisitCount);
            userRepository.save(user);
            operations.set(dbCountKey, totalVisitCount, 1, TimeUnit.DAYS); // 업데이트된 db_count로 Redis 초기화
            redisTemplate.delete(eventKey); // event_count 초기화
        }
    }

    // 청약 납입 호출 메서드
    public void incrementDeposit(Long userId, Long accountId, int depositAmount) {
        // Redis 키 구성
        String eventKey = "account:" + accountId + ":deposit";
        String dbCountKey = "account:" + accountId + ":dbDeposit";

        ValueOperations<String, Object> operations = redisTemplate.opsForValue();

        // 계좌 조회 및 유효성 확인
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        if (!account.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("Account does not belong to the user");
        }

        // Redis에서 납입 횟수 증가
        Integer redisDepositCount = Math.toIntExact(operations.increment(eventKey, 1)); // Long -> Integer 변환

        // dbCountKey 값이 없다면 DB에서 초기화
        Integer dbDepositCount = (Integer) operations.get(dbCountKey);
        if (dbDepositCount == null) {
            dbDepositCount = account.getDepositCount(); // account 테이블에서 납입 횟수 조회
            operations.set(dbCountKey, dbDepositCount, 1, TimeUnit.DAYS); // 하루 동안 유지
        }

        // DB와 Redis의 납입 횟수 합산
        int totalDepositCount = Optional.ofNullable(dbDepositCount).orElse(0) + redisDepositCount;

        // 뱃지 확인 및 할당
        checkAndAssignBadge(userId, totalDepositCount, "deposit");

        // 조건 만족 시 DB와 Redis의 dbCountKey를 업데이트
        if (totalDepositCount == 1 || totalDepositCount == 10 || totalDepositCount == 50 || totalDepositCount == 100) {
            account.setDepositCount(totalDepositCount); // 납입 횟수 업데이트
            operations.set(dbCountKey, totalDepositCount, 1, TimeUnit.DAYS); // 업데이트된 db_count로 Redis 초기화
            redisTemplate.delete(eventKey); // event_count 초기화
        }

        // 잔액 업데이트
        account.setBalance(account.getBalance() + depositAmount);
        accountRepository.save(account);
    }

    private void checkAndAssignBadge(Long userId, int count, String type) {
        // 모든 뱃지 조회
        List<Badge> badges = badgeRepository.findAll();

        for (Badge badge : badges) {
            boolean shouldAssignBadge = false;

            // 타입에 따라 조건 체크
            switch (type) {
                case "pageVisit":
                    if (badge.getBadgeName().equals("첫 발걸음") && count == 1) {
                        shouldAssignBadge = true;
                    } else if (badge.getBadgeName().equals("꾸준한 관심") && count == 10) {
                        shouldAssignBadge = true;
                    } else if (badge.getBadgeName().equals("청약 마스터") && count == 50) {
                        shouldAssignBadge = true;
                    } else if (badge.getBadgeName().equals("명예의 전당") && count == 100) {
                        shouldAssignBadge = true;
                    }
                    break;
                case "deposit": // deposit 조건 추가
                    if (badge.getBadgeName().equals("첫 저축") && count == 1) {
                        shouldAssignBadge = true;
                    } else if (badge.getBadgeName().equals("저축의 시작") && count == 10) {
                        shouldAssignBadge = true;
                    } else if (badge.getBadgeName().equals("꾸준한 저축가") && count == 50) {
                        shouldAssignBadge = true;
                    } else if (badge.getBadgeName().equals("저축의 달인") && count == 100) {
                        shouldAssignBadge = true;
                    }
                    break;
            }

            // 조건을 만족하면 뱃지를 사용자에게 할당
            if (shouldAssignBadge) {
                assignBadgeToUser(userId, badge);
            }
        }
    }

    @Transactional
    public void assignBadgeToUser(Long userId, Badge badge) {
        // 사용자와 연관된 뱃지 정보 조회
        User user = userRepository.findWithBadgesByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User with ID " + userId + " not found"));

        // 사용자에게 뱃지 추가 (addBadge 내부에서 중복 체크)
        user.addBadge(badge);

        // 변경된 사용자 저장 (Cascade 설정으로 UserBadge도 저장됨)
        userRepository.save(user);
    }
}
