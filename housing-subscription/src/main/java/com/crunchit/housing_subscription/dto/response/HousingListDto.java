package com.crunchit.housing_subscription.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HousingListDto {
    @JsonProperty
    private String house_manage_no;           // 주택관리번호
    @JsonProperty
    private String pblanc_no;                 // 공고번호
    @JsonProperty
    private String house_nm;                  // 주택명
    @JsonProperty
    private String house_secd;                // 주택구분코드
    @JsonProperty
    private String house_secd_nm;             // 주택구분코드명
    @JsonProperty
    private String house_dtl_secd;            // 주택상세구분코드
    @JsonProperty
    private String house_dtl_secd_nm;         // 주택상세구분코드명
    @JsonProperty
    private String rent_secd;                 // 분양구분코드
    @JsonProperty
    private String rent_secd_nm;              // 분양구분코드명
    @JsonProperty
    private String subscrpt_area_code_nm;     // 공급지역명
    @JsonProperty
    private String hssply_adres;              // 공급위치
    @JsonProperty
    private Integer tot_suply_hshldco;        // 공급규모
    @JsonProperty
    private String rcrit_pblanc_de;           // 모집공고일
    @JsonProperty
    private String subscrpt_rcept_bgnde;      // 청약접수시작일
    @JsonProperty
    private String subscrpt_rcept_endde;      // 청약접수종료일
    @JsonProperty
    private String rcept_bgnde;               // 청약접수시작일
    @JsonProperty
    private String rcept_endde;               // 청약접수종료일
}
