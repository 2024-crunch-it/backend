package com.crunchit.housing_subscription.dto.response;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class HousingAnnouncementDto {
    private String house_manage_no;              // 주택관리번호
    private String pblanc_no;                   // 공고번호
    private String house_nm;                    // 주택명
    private String house_secd;                  // 주택구분코드
    private String house_secd_nm;               // 주택구분코드명
    private String house_dtl_secd;              // 주택상세구분코드
    private String house_dtl_secd_nm;           // 주택상세구분코드명
    private String rent_secd;                   // 분양구분코드
    private String rent_secd_nm;                // 분양구분코드명
    private String subscrpt_area_code;          // 공급지역코드
    private String subscrpt_area_code_nm;       // 공급지역명
    private String hssply_zip;                  // 공급위치 우편번호
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
    private String przwner_presnatn_de;         // 당첨자발표일
    private String cntrct_cncls_bgnde;          // 계약시작일
    private String cntrct_cncls_endde;          // 계약종료일
    private String hmpg_adres;                  // 홈페이지주소
    private String cnstrct_entrps_nm;           // 건설업체명 (시공사)
    private String mdhs_telno;                  // 문의처
    private String bsns_mby_nm;                 // 사업주체명 (시행사)
    private String mvn_prearnge_yn;             // 입주예정월
    private String speclt_rdn_earth_at;         // 투기과열지구
    private String mdat_trget_area_secd;        // 조정대상지역
    private String parcprc_uls_at;              // 분양가상한제
    private String imprmn_bsns_at;              // 정비사업
    private String public_house_earth_at;       // 공공주택지구
    private String lrscl_bldlnd_at;             // 대규모 택지개발지구
    private String npln_prvopr_public_house_at; // 수도권 내 민영 공공주택지구
    private String public_house_spclm_applc_apt; // 공공주택 특별법 적용 여부
    private String pblanc_url;                  // 공고 URL

    private List<HousingModelDto> house_models;
}

