package com.crunchit.housing_subscription.repository;

import com.crunchit.housing_subscription.entity.HousingAnnouncement;
import com.crunchit.housing_subscription.entity.HousingAnnouncementId;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HousingAnnouncementRepository extends JpaRepository<HousingAnnouncement, HousingAnnouncementId> {

    @Query(value = """
        SELECT thp.*
        FROM tbl_housing_pblanc thp
        JOIN tbl_user_subscription_likes tusl
            ON thp.pblanc_no = tusl.pblanc_no
            AND thp.house_manage_no = tusl.house_manage_no
        WHERE tusl.user_id = :userId
        
        """, nativeQuery = true)
    Page<HousingAnnouncement> findAllWithLikes(@Param("userId") Long userId, PageRequest pageRequest);

    @Query(value = """
    SELECT * FROM tbl_housing_pblanc thp
    WHERE (rcept_bgnde BETWEEN :startDate AND :endDate)
       OR (rcept_endde BETWEEN :startDate AND :endDate)
       OR (subscrpt_rcept_bgnde BETWEEN :startDate AND :endDate)
       OR (subscrpt_rcept_endde BETWEEN :startDate AND :endDate)
""", nativeQuery = true)
    List<HousingAnnouncement> findByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query(value = """
    SELECT thp.* FROM tbl_housing_pblanc thp
        JOIN tbl_user_subscription_likes tusl
            ON thp.pblanc_no = tusl.pblanc_no
            AND thp.house_manage_no = tusl.house_manage_no
    WHERE (thp.rcept_bgnde BETWEEN :startDate AND :endDate)
       OR (thp.rcept_endde BETWEEN :startDate AND :endDate)
       OR (thp.subscrpt_rcept_bgnde BETWEEN :startDate AND :endDate)
       OR (thp.subscrpt_rcept_endde BETWEEN :startDate AND :endDate)
       AND tusl.user_id= :userId
""", nativeQuery = true)
    List<HousingAnnouncement> findByDateRangeLike(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("userId") Long userId);

    @Query(value = """
        SELECT * FROM tbl_housing_pblanc thp
        WHERE rcept_endde > CURRENT_TIMESTAMP()
           OR subscrpt_rcept_endde > CURRENT_TIMESTAMP()
        """, nativeQuery = true)
    List<HousingAnnouncement> findByEndDatesAfterNow();

    @Query(value = """
        SELECT thp.* FROM tbl_housing_pblanc thp
            JOIN tbl_user_subscription_likes tusl
                ON thp.pblanc_no = tusl.pblanc_no
                AND thp.house_manage_no = tusl.house_manage_no
        WHERE thp.rcept_endde > CURRENT_TIMESTAMP()
           OR thp.subscrpt_rcept_endde > CURRENT_TIMESTAMP()
           AND tusl.user_id= :userId
        """, nativeQuery = true)
    List<HousingAnnouncement> findByEndDatesAfterNowLike(@Param("userId") Long userId);
}
