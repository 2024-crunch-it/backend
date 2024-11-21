package com.crunchit.housing_subscription.dto.response;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HousingAnnouncementDto {
    private String house_manage_no;
    private String pblanc_no;
    private String house_nm;
    private String house_secd;
    private String house_secd_nm;
    private String house_dtl_secd;
    private String house_dtl_secd_nm;
    private String rent_secd;
    private String rent_secd_nm;
    private String subscrpt_area_code;
    private String subscrpt_area_code_nm;
    private String hssply_zip;
    private String hssply_adres;
    private Integer tot_suply_hshldco;
    private String rcrit_pblanc_de;
    private String subscrpt_rcept_bgnde;
    private String subscrpt_rcept_endde;
    private String rcept_bgnde;
    private String rcept_endde;
    private String spsply_rcept_bgnde;
    private String spsply_rcept_endde;
    private String gnrl_rnk1_crsparea_rcptde;
    private String gnrl_rnk1_crsparea_endde;
    private String gnrl_rnk1_etc_gg_rcptde;
    private String gnrl_rnk1_etc_gg_endde;
    private String gnrl_rnk1_etc_area_rcptde;
    private String gnrl_rnk1_etc_area_endde;
    private String gnrl_rnk2_crsparea_rcptde;
    private String gnrl_rnk2_crsparea_endde;
    private String gnrl_rnk2_etc_gg_rcptde;
    private String gnrl_rnk2_etc_gg_endde;
    private String gnrl_rnk2_etc_area_rcptde;
    private String gnrl_rnk2_etc_area_endde;
    private String przwner_presnatn_de;
    private String cntrct_cncls_bgnde;
    private String cntrct_cncls_endde;
    private String hmpg_adres;
    private String cnstrct_entrps_nm;
    private String mdhs_telno;
    private String bsns_mby_nm;
    private String mvn_prearnge_yn;
    private String speclt_rdn_earth_at;
    private String mdat_trget_area_secd;
    private String parcprc_uls_at;
    private String imprmn_bsns_at;
    private String public_house_earth_at;
    private String lrscl_bldlnd_at;
    private String npln_prvopr_public_house_at;
    private String public_house_spclm_applc_apt;
    private String pblanc_url;
    private Boolean isLiked;

    private List<HousingModelDto> house_models;

    // 전화번호 포맷팅 로직
    public String getMdhs_telno() {
        if (mdhs_telno == null || mdhs_telno.isEmpty()) {
            return mdhs_telno; // 값이 없으면 그대로 반환
        }

        int length = mdhs_telno.length();
        switch (length) {
            case 8:  // 4-4
                return mdhs_telno.replaceFirst("(\\d{4})(\\d{4})", "$1-$2");
            case 9:  // 2-3-4
                return mdhs_telno.replaceFirst("(\\d{2})(\\d{3})(\\d{4})", "$1-$2-$3");
            case 10: // 2-4-4
                return mdhs_telno.replaceFirst("(\\d{2})(\\d{4})(\\d{4})", "$1-$2-$3");
            case 11: // 3-4-4
                return mdhs_telno.replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
            default: // 포맷팅하지 않음
                return mdhs_telno;
        }
    }
}