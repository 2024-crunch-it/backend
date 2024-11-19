package com.crunchit.housing_subscription.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HousingMonthlyDto {
    private String house_manage_no;             // 주택관리번호
    private String pblanc_no;                   // 공고번호
    private String house_nm;                    // 주택명
    private String house_secd;                  // 주택구분코드
    private String house_secd_nm;               // 주택구분코드명
    private String house_dtl_secd;              // 주택상세구분코드
    private String house_dtl_secd_nm;           // 주택상세구분코드명
    private String rent_secd;                   // 분양구분코드
    private String rent_secd_nm;                // 분양구분코드명
    private String subscrpt_area_code_nm;       // 공급지역명
    private String hssply_adres;                // 공급위치
    private Integer tot_suply_hshldco;          // 공급규모
    private String rcrit_pblanc_de;             // 모집공고일
    private String subscrpt_rcept_bgnde;        // 청약접수시작일
    private String subscrpt_rcept_endde;        // 청약접수종료일
    private String rcept_bgnde;                 // 청약접수시작일
    private String rcept_endde;                 // 청약접수종료일
    private String spsply_rcept_bgnde;          // 특별공급 접수시작일
    private String spsply_rcept_endde;          // 특별공급 접수종료일
    private String gnrl_rnk1_crsparea_rcptde;   // 1순위 해당지역 접수시작일
    private String gnrl_rnk1_crsparea_endde;    // 1순위 해당지역 접수종료일
    private String gnrl_rnk1_etc_gg_rcptde;     // 1순위 경기지역 접수시작일
    private String gnrl_rnk1_etc_gg_endde;      // 1순위 경기지역 접수종료일
    private String gnrl_rnk1_etc_area_rcptde;   // 1순위 기타지역 접수시작일
    private String gnrl_rnk1_etc_area_endde;    // 1순위 기타지역 접수종료일
    private String gnrl_rnk2_crsparea_rcptde;   // 2순위 해당지역 접수시작일
    private String gnrl_rnk2_crsparea_endde;    // 2순위 해당지역 접수종료일
    private String gnrl_rnk2_etc_gg_rcptde;     // 2순위 경기지역 접수시작일
    private String gnrl_rnk2_etc_gg_endde;      // 2순위 경기지역 접수종료일
    private String gnrl_rnk2_etc_area_rcptde;   // 2순위 기타지역 접수시작일
    private String gnrl_rnk2_etc_area_endde;    // 2순위 기타지역 접수종료일
    private Boolean isLiked;
}
