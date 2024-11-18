package com.crunchit.housing_subscription.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class HousingAnnouncementId implements Serializable {

    @Column(name = "pblanc_no", nullable = false, length = 40)
    private String pblancNo;

    @Column(name = "house_manage_no", nullable = false, length = 40)
    private String houseManageNo;
}
