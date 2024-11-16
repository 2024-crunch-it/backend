package com.crunchit.housing_subscription.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Table(name = "tbl_user_subscription_likes")
@Getter
@Setter
@NoArgsConstructor
public class UserSubscriptionLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id", nullable = false)
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "pblanc_no", referencedColumnName = "pblanc_no", nullable = false),
            @JoinColumn(name = "house_manage_no", referencedColumnName = "house_manage_no", nullable = false)
    })
    private HousingAnnouncement housingAnnouncement;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "liked_at", nullable = false)
    private LocalDateTime likedAt = LocalDateTime.now();
}