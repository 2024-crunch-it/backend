package com.crunchit.housing_subscription.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HousingMappedDto {
    private String house_manage_no;           // 주택관리번호
    private String pblanc_no;                 // 공고번호
    private String house_nm;                  // 주택명
    private String house_secd;                // 주택구분코드
    private String house_secd_nm;             // 주택구분코드명
    private String house_dtl_secd;            // 주택상세구분코드
    private String house_dtl_secd_nm;         // 주택상세구분코드명
    private String rent_secd;                 // 분양구분코드
    private String rent_secd_nm;              // 분양구분코드명
    private String subscrpt_area_code_nm;     // 공급지역명
    private String hssply_adres;              // 공급위치
    private String hssply_zip;                // 공급위치 우편번호
    private Integer tot_suply_hshldco;        // 공급규모
    private String rcrit_pblanc_de;           // 모집공고일
    private String subscrpt_rcept_bgnde;      // 청약접수시작일
    private String subscrpt_rcept_endde;      // 청약접수종료일
    private String rcept_bgnde;               // 청약접수시작일
    private String rcept_endde;               // 청약접수종료일
    private Boolean isLiked;
}
