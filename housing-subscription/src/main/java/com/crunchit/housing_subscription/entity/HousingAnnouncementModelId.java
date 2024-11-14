package com.crunchit.housing_subscription.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class HousingAnnouncementModelId implements Serializable {
    @Column(name = "pblanc_no", nullable = false, length = 40)
    private String pblancNo;

    @Column(name = "house_manage_no", nullable = false, length = 40)
    private String houseManageNo;

    @Column(name = "model_no", nullable = false, length = 2)
    private String modelNo;
}
