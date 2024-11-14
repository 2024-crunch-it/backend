package com.crunchit.housing_subscription.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity // 복합키 클래스 참조
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_housing_pblanc")
@IdClass(HousingAnnouncementId.class)
public class HousingAnnouncement {

    @Id
    @Column(name = "pblanc_no", nullable = false, length = 40)
    private String pblancNo;

    @Id
    @Column(name = "house_manage_no", nullable = false, length = 40)
    private String houseManageNo;

    @Column(name = "house_nm", length = 200)
    private String houseNm;

    @Column(name = "house_secd", length = 2)
    private String houseSecd;

    @Column(name = "house_secd_nm", length = 4000)
    private String houseSecdNm;

    @Column(name = "house_dtl_secd", length = 2)
    private String houseDtlSecd;

    @Column(name = "house_dtl_secd_nm", length = 4000)
    private String houseDtlSecdNm;

    @Column(name = "rent_secd", length = 1)
    private String rentSecd;

    @Column(name = "rent_secd_nm", length = 500)
    private String rentSecdNm;

    @Column(name = "subscrpt_area_code", length = 3)
    private String subscrptAreaCode;

    @Column(name = "subscrpt_area_code_nm", length = 500)
    private String subscrptAreaCodeNm;

    @Column(name = "hssply_zip", length = 6)
    private String hssplyZip;

    @Column(name = "hssply_adres", length = 256)
    private String hssplyAdres;

    @Column(name = "tot_suply_hshldco")
    private Integer totSuplyHshldco;

    @Column(name = "rcrit_pblanc_de")
    private Date rcritPblancDe;

    @Column(name = "subscrpt_rcept_bgnde")
    private Date subscrptRceptBgnde;

    @Column(name = "subscrpt_rcept_endde")
    private Date subscrptRceptEndde;

    @Column(name = "rcept_bgnde")
    private Date rceptBgnde;

    @Column(name = "rcept_endde")
    private Date rceptEndde;

    @Column(name = "spsply_rcept_bgnde")
    private Date spsplyRceptBgnde;

    @Column(name = "spsply_rcept_endde")
    private Date spsplyRceptEndde;

    @Column(name = "gnrl_rnk1_crsparea_rcptde")
    private Date gnrlRnk1CrspareaRcptde;

    @Column(name = "gnrl_rnk1_crsparea_endde")
    private Date gnrlRnk1CrspareaEndde;

    @Column(name = "gnrl_rnk1_etc_gg_rcptde")
    private Date gnrlRnk1EtcGgRcptde;

    @Column(name = "gnrl_rnk1_etc_gg_endde")
    private Date gnrlRnk1EtcGgEndde;

    @Column(name = "gnrl_rnk1_etc_area_rcptde")
    private Date gnrlRnk1EtcAreaRcptde;

    @Column(name = "gnrl_rnk1_etc_area_endde")
    private Date gnrlRnk1EtcAreaEndde;

    @Column(name = "gnrl_rnk2_crsparea_rcptde")
    private Date gnrlRnk2CrspareaRcptde;

    @Column(name = "gnrl_rnk2_crsparea_endde")
    private Date gnrlRnk2CrspareaEndde;

    @Column(name = "gnrl_rnk2_etc_gg_rcptde")
    private Date gnrlRnk2EtcGgRcptde;

    @Column(name = "gnrl_rnk2_etc_gg_endde")
    private Date gnrlRnk2EtcGgEndde;

    @Column(name = "gnrl_rnk2_etc_area_rcptde")
    private Date gnrlRnk2EtcAreaRcptde;

    @Column(name = "gnrl_rnk2_etc_area_endde")
    private Date gnrlRnk2EtcAreaEndde;

    @Column(name = "przwner_presnatn_de")
    private Date przwnerPresnatnDe;

    @Column(name = "cntrct_cncls_bgnde")
    private Date cntrctCnclsBgnde;

    @Column(name = "cntrct_cncls_endde")
    private Date cntrctCnclsEndde;

    @Column(name = "hmpg_adres", length = 256)
    private String hmpgAdres;

    @Column(name = "cnstrct_entrps_nm", length = 200)
    private String cnstrctEntrpsNm;

    @Column(name = "mdhs_telno", length = 30)
    private String mdhsTelno;

    @Column(name = "bsns_mby_nm", length = 200)
    private String bsnsMbyNm;

    @Column(name = "mvn_prearnge_yn", length = 6)
    private String mvnPrearngeYn;

    @Column(name = "speclt_rdn_earth_at", length = 1)
    private String specltRdnEarthAt;

    @Column(name = "mdat_trget_area_secd", length = 1)
    private String mdatTrgetAreaSecd;

    @Column(name = "parcprc_uls_at", length = 1)
    private String parcprcUlsAt;

    @Column(name = "imprmn_bsns_at", length = 1)
    private String imprmnBsnsAt;

    @Column(name = "public_house_earth_at", length = 1)
    private String publicHouseEarthAt;

    @Column(name = "lrscl_bldlnd_at", length = 1)
    private String lrsclBldlndAt;

    @Column(name = "npln_prvopr_public_house_at", length = 1)
    private String nplnPrvoprPublicHouseAt;

    @Column(name = "public_house_spclm_applc_apt", length = 1)
    private String publicHouseSpclmApplcApt;

    @Column(name = "pblanc_url", length = 300)
    private String pblancUrl;
}
