package com.crunchit.housing_subscription.service;

import com.crunchit.housing_subscription.dto.response.UserSubscriptionLikeResponseDto;
import com.crunchit.housing_subscription.entity.*;
import com.crunchit.housing_subscription.repository.BadgeRepository;
import com.crunchit.housing_subscription.repository.HousingAnnouncementRepository;
import com.crunchit.housing_subscription.repository.UserRepository;
import com.crunchit.housing_subscription.repository.UserSubscriptionLikeRepository;
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
public class UserSubscriptionLikeService {

    private final UserRepository userRepository;
    private final HousingAnnouncementRepository housingAnnouncementRepository;
    private final UserSubscriptionLikeRepository likeRepository;
    private final NotificationScheduleService notificationScheduleService; // 알림 관련 추가
    private final BadgeRepository badgeRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 찜 목록 추가
     */
    @Transactional
    public void addLike(Long userId, String pblancNo, String houseManageNo) {
        // 사용자 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 청약 정보 확인
        HousingAnnouncement announcement = housingAnnouncementRepository.findById(new HousingAnnouncementId(pblancNo, houseManageNo))
                .orElseThrow(() -> new RuntimeException("Housing announcement not found"));

        // 찜 여부 확인
        if (likeRepository.existsByUserAndHousingAnnouncement(user, announcement)) {
            throw new RuntimeException("Already added to the wishlist");
        }

        // 찜 추가
        UserSubscriptionLike like = new UserSubscriptionLike();
        like.setUser(user);
        like.setHousingAnnouncement(announcement);
        likeRepository.save(like);

        incrementBookmarkCount(userId);

        // 알림 스케줄 생성 추가
        notificationScheduleService.createNotificationSchedules(user, announcement);
    }

    /**
     * 찜 삭제
     */
    @Transactional
    public void removeLike(Long userId, String pblancNo, String houseManageNo) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        HousingAnnouncement announcement = housingAnnouncementRepository.findById(new HousingAnnouncementId(pblancNo, houseManageNo))
                .orElseThrow(() -> new RuntimeException("Housing announcement not found"));

        UserSubscriptionLike like = likeRepository.findByUserAndHousingAnnouncement(user, announcement)
                .orElseThrow(() -> new RuntimeException("Wishlist item not found"));

        likeRepository.delete(like);
        decrementBookmarkCount(userId);
    }

    /**
     * 내가 찜한 전체 목록 조회
     */
    @Transactional(readOnly = true)
    public List<UserSubscriptionLikeResponseDto> getAllLikesByUser(Long userId) {
        List<UserSubscriptionLike> likes = likeRepository.findAllByUser_UserId(userId);
        return likes.stream()
                .map(like -> new UserSubscriptionLikeResponseDto(
                        like.getUser().getUserId(),
                        like.getUser().getUserName(),
                        like.getHousingAnnouncement().getPblancNo(),
                        like.getHousingAnnouncement().getHouseManageNo(),
                        like.getLikedAt()
                ))
                .collect(Collectors.toList());
    }

    // 찜하기 api시 호출되는 메서드
    public void incrementBookmarkCount(Long userId) {
        String eventKey = "user:" + userId + ":bookmark";
        String dbCountKey = "user:" + userId + ":dbBookmark";

        ValueOperations<String, Object> operations = redisTemplate.opsForValue();

        // Redis 에서 방문 횟수 증가
        Integer redisVisitCount = Optional.ofNullable(operations.get(eventKey))
                .map(Object::toString)
                .map(Integer::valueOf)
                .orElse(0);

        operations.increment(eventKey, 1); // Redis 에서 1 증가

        // dbCountKey 값이 없다면 DB 에서 조회하여 초기화
        Integer dbVisitCount = Optional.ofNullable(operations.get(dbCountKey))
                .map(Object::toString)
                .map(Integer::valueOf)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
                    int count = user.getBookmarkCount();
                    operations.set(dbCountKey, count, 1, TimeUnit.DAYS); // 하루 동안 유지
                    return count;
                });

        // DB와 Redis 의 카운트를 합산하여 조건 체크
        int totalBookmarkCount = Optional.ofNullable(dbVisitCount).orElse(0) + redisVisitCount;
        checkAndAssignBadge(userId, totalBookmarkCount, "bookmark");


//         조건 만족 시 DB와 Redis의 dbCountKey를 업데이트
        if (totalBookmarkCount == 1 || totalBookmarkCount == 10 || totalBookmarkCount == 50 || totalBookmarkCount == 100) {
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            user.setBookmarkCount(totalBookmarkCount);
            userRepository.save(user);
            operations.set(dbCountKey, totalBookmarkCount, 1, TimeUnit.DAYS); // 업데이트된 db_count로 Redis 초기화
            redisTemplate.delete(eventKey); // event_count 초기화
        }
    }

    public void decrementBookmarkCount(Long userId) {
        String eventKey = "user:" + userId + ":bookmark";
        String dbCountKey = "user:" + userId + ":dbBookmark";

        ValueOperations<String, Object> operations = redisTemplate.opsForValue();

        // Redis에서 방문 횟수 감소
        Integer redisVisitCount = (Integer) operations.get(eventKey);

        // Redis 값이 null이거나 0이면 DB에서 직접 감소 처리
        if (redisVisitCount == null || redisVisitCount <= 0) {
            // DB에서 찜 카운트 감소
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            int dbBookmarkCount = user.getBookmarkCount();

            // 찜 카운트가 0보다 작아지지 않도록 제한
            if (dbBookmarkCount > 0) {
                user.setBookmarkCount(dbBookmarkCount - 1);
                userRepository.save(user);
            }

            // Redis의 dbCountKey 초기화
            operations.set(dbCountKey, user.getBookmarkCount(), 1, TimeUnit.DAYS); // 업데이트된 값으로 하루 동안 유지
        } else {
            // Redis에서 값 감소
            redisVisitCount = Math.toIntExact(operations.increment(eventKey, -1)); // 감소 연산
            if (redisVisitCount < 0) {
                operations.set(eventKey, 0); // Redis 값이 음수로 떨어지지 않도록 초기화
            }
        }
    }

    private void checkAndAssignBadge(Long userId, int count, String type) {
        // 모든 뱃지 조회
        List<Badge> badges = badgeRepository.findAll();

        for (Badge badge : badges) {
            boolean shouldAssignBadge = false;

            // 타입에 따라 조건 체크
            switch (type) {
                case "bookmark":
                    if (badge.getBadgeName().equals("첫 관심 등록") && count == 1) {
                        shouldAssignBadge = true;
                    } else if (badge.getBadgeName().equals("꾸준한 관심자") && count == 10) {
                        shouldAssignBadge = true;
                    } else if (badge.getBadgeName().equals("청약 정보 애호가") && count == 50) {
                        shouldAssignBadge = true;
                    } else if (badge.getBadgeName().equals("청약 정보 수집가") && count == 100) {
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