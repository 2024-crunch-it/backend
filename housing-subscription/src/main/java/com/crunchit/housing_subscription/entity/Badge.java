package com.crunchit.housing_subscription.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_badge")
@Getter
@Setter
@NoArgsConstructor
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id", nullable = false)
    private Long badgeId;

    @Column(name = "badge_number", nullable = false)
    private int badgeNumber;

    @Column(name = "badge_name", nullable = false)
    private String badgeName;

    @OneToMany(mappedBy = "badge", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserBadge> userBadges = new HashSet<>();
}