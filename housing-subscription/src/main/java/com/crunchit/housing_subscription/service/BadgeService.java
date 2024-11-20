package com.crunchit.housing_subscription.service;

import com.crunchit.housing_subscription.dto.response.BadgeDto;
import com.crunchit.housing_subscription.dto.response.UserResponseDto;
import com.crunchit.housing_subscription.entity.Account;
import com.crunchit.housing_subscription.entity.Badge;
import com.crunchit.housing_subscription.entity.Deposit;
import com.crunchit.housing_subscription.entity.User;
import com.crunchit.housing_subscription.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BadgeService {

    private final UserRepository userRepository;
    private final BadgeRepository badgeRepository;
    private final AccountRepository accountRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final NotificationService notificationService;
    private final DepositRepository depositRepository;

    @Transactional(readOnly = true)
    public UserResponseDto getUserWithBadges(Long userId) {
        User user = userRepository.findWithBadgesByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 전체 뱃지 조회
        List<Badge> allBadges = badgeRepository.findAll();

        // 사용자가 보유한 뱃지 ID 추출
        Set<Integer> ownedBadgeNumbers = user.getUserBadges().stream()
                .map(userBadge -> userBadge.getBadge().getBadgeNumber())
                .collect(Collectors.toSet());

        // 보유한 뱃지와 보유하지 않은 뱃지를 분리
        List<BadgeDto> ownedBadges = new ArrayList<>();
        List<BadgeDto> unownedBadges = new ArrayList<>();

        for (Badge badge : allBadges) {
            if (ownedBadgeNumbers.contains(badge.getBadgeNumber())) {
                ownedBadges.add(new BadgeDto(
                        badge.getBadgeNumber(),
                        badge.getBadgeName(),
                        LocalDateTime.now() // 실제 획득 날짜가 있다면 UserBadge에서 가져와야 함
                ));
            } else {
                unownedBadges.add(new BadgeDto(
                        badge.getBadgeNumber(),
                        badge.getBadgeName(),
                        null // 보유하지 않은 뱃지는 획득 날짜 없음
                ));
            }
        }

        // 결과 반환
        return new UserResponseDto(user.getUserId(), user.getUserName(), ownedBadges, unownedBadges);
    }

    // 주택청약 페이지 방문 시 호출되는 메서드
    public void incrementPageVisit(Long userId) {
        String eventKey = "user:" + userId + ":pageVisit";
        String dbCountKey = "user:" + userId + ":dbPageVisit";

        ValueOperations<String, Object> operations = redisTemplate.opsForValue();

        Integer redisUsageCount = Optional.ofNullable(operations.get(eventKey))
                .map(Object::toString)
                .map(Integer::valueOf)
                .orElse(0);

        operations.increment(eventKey, 1);

        Integer dbUsageCount = Optional.ofNullable(operations.get(dbCountKey))
            .map(Object::toString)
            .map(Integer::valueOf)
            .orElseGet(() -> {
                User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
                int count = user.getCalendarUsageCount();
                operations.set(dbCountKey, count, 1, TimeUnit.DAYS); // 하루 동안 유지
                return count;
            });

        // DB와 Redis의 사용 횟수를 합산
        int totalUsageCount = dbUsageCount + redisUsageCount+1;

        checkAndAssignBadge(userId, totalUsageCount, "pageVisit");

        if (totalUsageCount == 1 || totalUsageCount == 10 || totalUsageCount == 50 || totalUsageCount == 100) {
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            user.setPageVisitCount(totalUsageCount);
            userRepository.save(user);
            operations.set(dbCountKey, totalUsageCount, 1, TimeUnit.DAYS); // 업데이트된 db_count로 Redis 초기화
            redisTemplate.delete(eventKey); // event_count 초기화
        }
    }

    // 청약 납입 호출 메서드
    @Transactional
    public void incrementDeposit(Long userId, Long accountId, int depositAmount) {

        // 계좌 조회
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));

        // 입금 기록 생성
        Deposit deposit = Deposit.builder()
                .account(account)
                .depositAmount(depositAmount)
                .depositDate(LocalDateTime.now())
                .build();

        depositRepository.save(deposit);

        // 계좌 잔액 업데이트
        account.setBalance(account.getBalance() + depositAmount);
        accountRepository.save(account); // 변경 감지에 의해 자동으로 업데이트되지만 명시적으로 저장

        // 납입 크기를 count에 넣어서 뱃지 수여 여부 체크
        checkAndAssignBadge(userId, account.getDeposits().size(), "deposit");
    }

    // 청약 캘린더 기능 사용 횟수 증가 메서드
    public void incrementCalendarUsage(Long userId) {
        String eventKey = "user:" + userId + ":calendarUsage";
        String dbCountKey = "user:" + userId + ":dbCalendarUsage";

        ValueOperations<String, Object> operations = redisTemplate.opsForValue();

        // Redis에서 캘린더 사용 횟수 증가
        Integer redisUsageCount = Optional.ofNullable(operations.get(eventKey))
                .map(Object::toString)
                .map(Integer::valueOf)
                .orElse(0);

        operations.increment(eventKey, 1); // Redis에서 1 증가

        // dbCountKey 값이 없다면 DB에서 조회하여 초기화
        Integer dbUsageCount = Optional.ofNullable(operations.get(dbCountKey))
                .map(Object::toString)
                .map(Integer::valueOf)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
                    int count = user.getCalendarUsageCount();
                    operations.set(dbCountKey, count, 1, TimeUnit.DAYS); // 하루 동안 유지
                    return count;
                });

        // DB와 Redis의 사용 횟수를 합산
        int totalUsageCount = dbUsageCount + redisUsageCount+1;


        // 10번 조건 체크 및 뱃지 수여
        if (totalUsageCount == 10) {
            checkAndAssignBadge(userId, totalUsageCount, "calendar");

            // DB 업데이트 및 Redis 초기화
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            user.setCalendarUsageCount(totalUsageCount);
            userRepository.save(user);

            operations.set(dbCountKey, totalUsageCount, 1, TimeUnit.DAYS); // 업데이트된 db_count로 Redis 초기화
            redisTemplate.delete(eventKey); // event_count 초기화
        }
    }

    @Transactional
    public void incrementCustomAlertUsage(Long userId) {
        String eventKey = "user:" + userId + ":customAlertUsage";
        String dbCountKey = "user:" + userId + ":dbCustomAlertUsage";

        ValueOperations<String, Object> operations = redisTemplate.opsForValue();

        // Redis에서 캘린더 사용 횟수 증가
        Integer redisUsageCount = Optional.ofNullable(operations.get(eventKey))
                .map(Object::toString)
                .map(Integer::valueOf)
                .orElse(0);

        operations.increment(eventKey, 1); // Redis에서 1 증가

        // dbCountKey 값이 없다면 DB에서 조회하여 초기화
        Integer dbUsageCount = Optional.ofNullable(operations.get(dbCountKey))
                .map(Object::toString)
                .map(Integer::valueOf)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
                    int count = user.getCustomAlertUsageCount();
                    operations.set(dbCountKey, count, 1, TimeUnit.DAYS); // 하루 동안 유지
                    return count;
                });

        // DB와 Redis의 사용 횟수를 합산
        int totalUsageCount = dbUsageCount + redisUsageCount +1;

        // 조건 만족 시 DB 업데이트 및 Redis 초기화
        if (totalUsageCount == 10) {
            checkAndAssignBadge(userId, totalUsageCount, "alert");

            // DB 업데이트 및 Redis 초기화
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            user.setCustomAlertUsageCount(totalUsageCount);
            userRepository.save(user);

            operations.set(dbCountKey, totalUsageCount, 1, TimeUnit.DAYS); // 업데이트된 db_count로 Redis 초기화
            redisTemplate.delete(eventKey); // event_count 초기화
        }
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
                case "calendar":
                    if (badge.getBadgeName().equals("청약 캘린더 마스터") && count == 10) {
                        shouldAssignBadge = true;
                    }
                case "alert":
                    if (badge.getBadgeName().equals("맞춤 알림 활용자") && count == 10) {
                        shouldAssignBadge = true;
                    }
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
        boolean flag = user.addBadge(badge);

        if (flag) {
            // 변경된 사용자 저장 (Cascade 설정으로 UserBadge 도 저장됨)
            userRepository.save(user);

            // 뱃지 획득 알림 발송
            notificationService.sendBadgeNotification(user, badge.getBadgeName());
        }
    }
}
