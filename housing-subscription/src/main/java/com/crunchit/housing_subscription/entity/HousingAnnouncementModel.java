package com.crunchit.housing_subscription.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tbl_housing_pblanc_model")
@IdClass(HousingAnnouncementModelId.class)
public class HousingAnnouncementModel {

    @Id
    @Column(name = "pblanc_no", nullable = false, length = 40)
    private String pblancNo;

    @Id
    @Column(name = "house_manage_no", nullable = false, length = 40)
    private String houseManageNo;

    @Id
    @Column(name = "model_no", nullable = false, length = 2)
    private String modelNo;

    @Column(name = "house_ty")
    private String houseTy;

    @Column(name = "suply_ar")
    private Double suplyAr;

    @Column(name = "suply_hshldco")
    private Integer suplyHshldco;

    @Column(name = "spsply_hshldco")
    private Integer spsplyHshldco;

    @Column(name = "mnych_hshldco")
    private Integer mnychHshldco;

    @Column(name = "nwwds_hshldco")
    private Integer nwwdsHshldco;

    @Column(name = "lfe_frst_hshldco")
    private Integer lfeFrstHshldco;

    @Column(name = "old_parnts_suport_hshldco")
    private Integer oldParntsSuportHshldco;

    @Column(name = "instt_recomend_hshldco")
    private Integer insttRecomendHshldco;

    @Column(name = "etc_hshldco")
    private Integer etcHshldco;

    @Column(name = "transr_instt_enfsn_hshldco")
    private Integer transrInsttEnfsnHshldco;

    @Column(name = "ygmn_hshldco")
    private Integer ygmnHshldco;

    @Column(name = "nwbb_hshldco")
    private Integer nwbbHshldco;

    @Column(name = "lttot_top_amount")
    private Double lttotTopAmount;
}
