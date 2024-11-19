package com.crunchit.housing_subscription.service;

import com.crunchit.housing_subscription.dto.response.BadgeDto;
import com.crunchit.housing_subscription.dto.response.UserDesiredAreaResponseDto;
import com.crunchit.housing_subscription.dto.response.UserResponseDto;
import com.crunchit.housing_subscription.entity.User;
import com.crunchit.housing_subscription.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDesiredAreaResponseDto getDesiredAreaByUser(Long userId) {
        User user = userRepository.findWithBadgesByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return UserDesiredAreaResponseDto.fromEntity(user);
    }

    @Transactional
    public void updateDesiredArea(Long userId, int desiredArea) {
        // 유효성 검사 (예: desiredArea가 0~3 사이인지 확인)
        if (desiredArea < 0 || desiredArea > 3) {
            throw new IllegalArgumentException("유효하지 않은 desiredArea 값입니다. 0~3 사이의 값을 입력하세요.");
        }

        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. ID: " + userId));

        // desiredArea 업데이트
        user.setDesiredArea(desiredArea);

    }

    @Transactional(readOnly = true)
    public int getUserBadgeCount(Long userId) {
        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. ID: " + userId));

        // userBadges 개수 반환
        return user.getUserBadges().size();
    }
}
