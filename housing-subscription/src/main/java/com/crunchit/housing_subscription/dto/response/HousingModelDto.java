package com.crunchit.housing_subscription.dto.response;

import com.crunchit.housing_subscription.util.CustomLongDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HousingModelDto {
    private String house_manage_no;              // 주택관리번호
    private String pblanc_no;                   // 공고번호
    private String model_no;                    // 모델번호
    private String house_ty;                    // 주택형
    private Double suply_ar;                    // 공급면적
    private Integer suply_hshldco;              // 일반공급세대수
    private Integer spsply_hshldco;             // 특별공급세대수
    private Integer mnych_hshldco;              // 특별공급-다자녀가구 세대수
    private Integer nwwds_hshldco;              // 특별공급-신혼부부 세대수
    private Integer lfe_frst_hshldco;           // 특별공급-생애최초 세대수
    private Integer old_parnts_suport_hshldco;  // 특별공급-노부모부양 세대수
    private Integer instt_recomend_hshldco;     // 특별공급-기관추천 세대수
    private Integer etc_hshldco;                // 특별공급-기타 세대수
    private Integer transr_instt_enfsn_hshldco; // 특별공급-이전기관 세대수
    private Integer ygmn_hshldco;               // 특별공급-청년 세대수
    private Integer nwbb_hshldco;               // 특별공급-신생아 세대수
    private Double lttot_top_amount;              // 공급금액 (분양최고금액) (단위:만원)
}

