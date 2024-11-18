package com.crunchit.housing_subscription.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_user")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "page_visit_count", nullable = false)
    private int pageVisitCount;

    @Column(name = "bookmark_count", nullable = false)
    private int bookmarkCount;

    @Column(name = "calendar_usage_count", nullable = false)
    private int calendarUsageCount;

    @Column(name = "custom_alert_usage_count", nullable = false)
    private int customAlertUsageCount;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserBadge> userBadges = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserSubscriptionLike> likes = new HashSet<>();

    // 연관관계 관리 메서드
    public void addBadge(Badge badge) {
        // 중복 검사 후 새로운 뱃지 추가
        if (userBadges.stream().noneMatch(userBadge -> userBadge.getBadge().equals(badge))) {
            UserBadge userBadge = new UserBadge();
            userBadge.setUser(this); // 연관 설정
            userBadge.setBadge(badge); // 연관 설정
            this.userBadges.add(userBadge);
        }
    }
}