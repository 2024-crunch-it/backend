package com.crunchit.housing_subscription.dto.externalApi.response;

import com.crunchit.housing_subscription.entity.HousingAnnouncement;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class HousingAnnouncementApiDto {
    @JsonProperty
    private String HOUSE_MANAGE_NO;
    @JsonProperty// 주택관리번호
    private String PBLANC_NO;                  // 공고번호
    @JsonProperty
    private String HOUSE_NM;                   // 주택명
    @JsonProperty
    private String HOUSE_SECD;                 // 주택구분코드
    @JsonProperty
    private String HOUSE_SECD_NM;              // 주택구분코드명
    @JsonProperty
    private String HOUSE_DTL_SECD;             // 주택상세구분코드
    @JsonProperty
    private String HOUSE_DTL_SECD_NM;          // 주택상세구분코드명
    @JsonProperty
    private String RENT_SECD;                  // 분양구분코드
    @JsonProperty
    private String RENT_SECD_NM;               // 분양구분코드명
    @JsonProperty
    private String SUBSCRPT_AREA_CODE;         // 공급지역코드
    @JsonProperty
    private String SUBSCRPT_AREA_CODE_NM;      // 공급지역명
    @JsonProperty
    private String HSSPLY_ZIP;                 // 공급위치 우편번호
    @JsonProperty
    private String HSSPLY_ADRES;               // 공급위치
    @JsonProperty
    private Integer TOT_SUPLY_HSHLDCO;         // 공급규모
    @JsonProperty
    private String RCRIT_PBLANC_DE;            // 모집공고일
    @JsonProperty
    private String SUBSCRPT_RCEPT_BGNDE;       // 청약접수시작일
    @JsonProperty
    private String SUBSCRPT_RCEPT_ENDDE;       // 청약접수종료일
    @JsonProperty
    private String RCEPT_BGNDE;                // 청약접수시작일
    @JsonProperty
    private String RCEPT_ENDDE;                // 청약접수종료일
    @JsonProperty
    private String SPSPLY_RCEPT_BGNDE;         // 특별공급 접수시작일
    @JsonProperty
    private String SPSPLY_RCEPT_ENDDE;         // 특별공급 접수종료일
    @JsonProperty
    private String GNRL_RNK1_CRSPAREA_RCPTDE;  // 1순위 해당지역 접수시작일
    @JsonProperty
    private String GNRL_RNK1_CRSPAREA_ENDDE;   // 1순위 해당지역 접수종료일
    @JsonProperty
    private String GNRL_RNK1_ETC_GG_RCPTDE;    // 1순위 경기지역 접수시작일
    @JsonProperty
    private String GNRL_RNK1_ETC_GG_ENDDE;     // 1순위 경기지역 접수종료일
    @JsonProperty
    private String GNRL_RNK1_ETC_AREA_RCPTDE;  // 1순위 기타지역 접수시작일
    @JsonProperty
    private String GNRL_RNK1_ETC_AREA_ENDDE;   // 1순위 기타지역 접수종료일
    @JsonProperty
    private String GNRL_RNK2_CRSPAREA_RCPTDE;  // 2순위 해당지역 접수시작일
    @JsonProperty
    private String GNRL_RNK2_CRSPAREA_ENDDE;   // 2순위 해당지역 접수종료일
    @JsonProperty
    private String GNRL_RNK2_ETC_GG_RCPTDE;    // 2순위 경기지역 접수시작일
    @JsonProperty
    private String GNRL_RNK2_ETC_GG_ENDDE;     // 2순위 경기지역 접수종료일
    @JsonProperty
    private String GNRL_RNK2_ETC_AREA_RCPTDE;  // 2순위 기타지역 접수시작일
    @JsonProperty
    private String GNRL_RNK2_ETC_AREA_ENDDE;   // 2순위 기타지역 접수종료일
    @JsonProperty
    private String PRZWNER_PRESNATN_DE;        // 당첨자발표일
    @JsonProperty
    private String CNTRCT_CNCLS_BGNDE;         // 계약시작일
    @JsonProperty
    private String CNTRCT_CNCLS_ENDDE;         // 계약종료일
    @JsonProperty
    private String HMPG_ADRES;                 // 홈페이지주소
    @JsonProperty
    private String CNSTRCT_ENTRPS_NM;          // 건설업체명 (시공사)
    @JsonProperty
    private String MDHS_TELNO;                 // 문의처
    @JsonProperty
    private String BSNS_MBY_NM;                // 사업주체명 (시행사)
    @JsonProperty
    private String MVN_PREARNGE_YN;            // 입주예정월
    @JsonProperty
    private String SPECLT_RDN_EARTH_AT;        // 투기과열지구
    @JsonProperty
    private String MDAT_TRGET_AREA_SECD;       // 조정대상지역
    @JsonProperty
    private String PARCPRC_ULS_AT;             // 분양가상한제
    @JsonProperty
    private String IMPRMN_BSNS_AT;             // 정비사업
    @JsonProperty
    private String PUBLIC_HOUSE_EARTH_AT;      // 공공주택지구
    @JsonProperty
    private String LRSCL_BLDLND_AT;            // 대규모 택지개발지구
    @JsonProperty
    private String NPLN_PRVOPR_PUBLIC_HOUSE_AT; // 수도권 내 민영 공공주택지구
    @JsonProperty
    private String PUBLIC_HOUSE_SPCLM_APPLC_APT; // 공공주택 특별법 적용 여부
    @JsonProperty
    private String PBLANC_URL;                 // 모집공고 상세 URL

    public HousingAnnouncement toEntity(){
        return HousingAnnouncement.builder()
                .pblancNo(this.PBLANC_NO)
                .houseManageNo(this.HOUSE_MANAGE_NO)
                .houseNm(this.HOUSE_NM)
                .houseSecd(this.HOUSE_SECD)
                .houseSecdNm(this.HOUSE_SECD_NM)
                .houseDtlSecd(this.HOUSE_DTL_SECD)
                .houseDtlSecdNm(this.HOUSE_DTL_SECD_NM)
                .rentSecd(this.RENT_SECD)
                .rentSecdNm(this.RENT_SECD_NM)
                .subscrptAreaCode(this.SUBSCRPT_AREA_CODE)
                .subscrptAreaCodeNm(this.SUBSCRPT_AREA_CODE_NM)
                .hssplyZip(this.HSSPLY_ZIP)
                .hssplyAdres(this.HSSPLY_ADRES)
                .totSuplyHshldco(this.TOT_SUPLY_HSHLDCO)
                .rcritPblancDe(parseDate(this.RCRIT_PBLANC_DE))
                .subscrptRceptBgnde(parseDate(this.SUBSCRPT_RCEPT_BGNDE))
                .subscrptRceptEndde(parseDate(this.SUBSCRPT_RCEPT_ENDDE))
                .rceptBgnde(parseDate(this.RCEPT_BGNDE))
                .rceptEndde(parseDate(this.RCEPT_ENDDE))
                .spsplyRceptBgnde(parseDate(this.SPSPLY_RCEPT_BGNDE))
                .spsplyRceptEndde(parseDate(this.SPSPLY_RCEPT_ENDDE))
                .gnrlRnk1CrspareaRcptde(parseDate(this.GNRL_RNK1_CRSPAREA_RCPTDE))
                .gnrlRnk1CrspareaEndde(parseDate(this.GNRL_RNK1_CRSPAREA_ENDDE))
                .gnrlRnk1EtcGgRcptde(parseDate(this.GNRL_RNK1_ETC_GG_RCPTDE))
                .gnrlRnk1EtcGgEndde(parseDate(this.GNRL_RNK1_ETC_GG_ENDDE))
                .gnrlRnk1EtcAreaRcptde(parseDate(this.GNRL_RNK1_ETC_AREA_RCPTDE))
                .gnrlRnk1EtcAreaEndde(parseDate(this.GNRL_RNK1_ETC_AREA_ENDDE))
                .gnrlRnk2CrspareaRcptde(parseDate(this.GNRL_RNK2_CRSPAREA_RCPTDE))
                .gnrlRnk2CrspareaEndde(parseDate(this.GNRL_RNK2_CRSPAREA_ENDDE))
                .gnrlRnk2EtcGgRcptde(parseDate(this.GNRL_RNK2_ETC_GG_RCPTDE))
                .gnrlRnk2EtcGgEndde(parseDate(this.GNRL_RNK2_ETC_GG_ENDDE))
                .gnrlRnk2EtcAreaRcptde(parseDate(this.GNRL_RNK2_ETC_AREA_RCPTDE))
                .gnrlRnk2EtcAreaEndde(parseDate(this.GNRL_RNK2_ETC_AREA_ENDDE))
                .przwnerPresnatnDe(parseDate(this.PRZWNER_PRESNATN_DE))
                .cntrctCnclsBgnde(parseDate(this.CNTRCT_CNCLS_BGNDE))
                .cntrctCnclsEndde(parseDate(this.CNTRCT_CNCLS_ENDDE))
                .hmpgAdres(this.HMPG_ADRES)
                .cnstrctEntrpsNm(this.CNSTRCT_ENTRPS_NM)
                .mdhsTelno(this.MDHS_TELNO)
                .bsnsMbyNm(this.BSNS_MBY_NM)
                .mvnPrearngeYn(this.MVN_PREARNGE_YN)
                .specltRdnEarthAt(this.SPECLT_RDN_EARTH_AT)
                .mdatTrgetAreaSecd(this.MDAT_TRGET_AREA_SECD)
                .parcprcUlsAt(this.PARCPRC_ULS_AT)
                .imprmnBsnsAt(this.IMPRMN_BSNS_AT)
                .publicHouseEarthAt(this.PUBLIC_HOUSE_EARTH_AT)
                .lrsclBldlndAt(this.LRSCL_BLDLND_AT)
                .nplnPrvoprPublicHouseAt(this.NPLN_PRVOPR_PUBLIC_HOUSE_AT)
                .publicHouseSpclmApplcApt(this.PUBLIC_HOUSE_SPCLM_APPLC_APT)
                .pblancUrl(this.PBLANC_URL).build();
    }
    private Date parseDate(String dateStr) {
        if (dateStr == null) return null;

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf1.parse(dateStr);
        } catch (ParseException ignored) {

        }
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
        try {
            return sdf2.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }
}
