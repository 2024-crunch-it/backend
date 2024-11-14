package com.crunchit.housing_subscription.service;

import com.crunchit.housing_subscription.dto.response.BadgeDto;
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
}