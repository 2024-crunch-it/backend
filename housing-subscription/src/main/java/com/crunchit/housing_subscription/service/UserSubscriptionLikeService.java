package com.crunchit.housing_subscription.service;

import com.crunchit.housing_subscription.dto.response.UserSubscriptionLikeResponseDto;
import com.crunchit.housing_subscription.entity.HousingAnnouncement;
import com.crunchit.housing_subscription.entity.HousingAnnouncementId;
import com.crunchit.housing_subscription.entity.User;
import com.crunchit.housing_subscription.entity.UserSubscriptionLike;
import com.crunchit.housing_subscription.repository.HousingAnnouncementRepository;
import com.crunchit.housing_subscription.repository.UserRepository;
import com.crunchit.housing_subscription.repository.UserSubscriptionLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserSubscriptionLikeService {

    private final UserRepository userRepository;
    private final HousingAnnouncementRepository housingAnnouncementRepository;
    private final UserSubscriptionLikeRepository likeRepository;
    private final NotificationScheduleService notificationScheduleService; // 알림 관련 추가

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

}