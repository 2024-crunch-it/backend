package com.crunchit.housing_subscription.dto.externalApi.response;

import com.crunchit.housing_subscription.entity.HousingAnnouncementModel;
import com.crunchit.housing_subscription.util.CustomLongDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

@Data
public class HousingModelApiDto {
    @JsonProperty
    private String HOUSE_MANAGE_NO; // 주택관리번호
    @JsonProperty
    private String PBLANC_NO; // 공고번호
    @JsonProperty
    private String MODEL_NO; // 모델번호
    @JsonProperty
    private String HOUSE_TY; // 주택형
    @JsonProperty
    private Double SUPLY_AR; // 공급면적
    @JsonProperty
    private Integer SUPLY_HSHLDCO; // 일반공급세대수
    @JsonProperty
    private Integer SPSPLY_HSHLDCO; // 특별공급세대수
    @JsonProperty
    private Integer MNYCH_HSHLDCO; // 특별공급-다자녀가구 세대수
    @JsonProperty
    private Integer NWWDS_HSHLDCO; // 특별공급-신혼부부 세대수
    @JsonProperty
    private Integer LFE_FRST_HSHLDCO; // 특별공급-생애최초 세대수
    @JsonProperty
    private Integer OLD_PARNTS_SUPORT_HSHLDCO; // 특별공급-노부모부양 세대수
    @JsonProperty
    private Integer INSTT_RECOMEND_HSHLDCO; // 특별공급-기관추천 세대수
    @JsonProperty
    private Integer ETC_HSHLDCO; // 특별공급-기타 세대수
    @JsonProperty
    private Integer TRANSR_INSTT_ENFSN_HSHLDCO; // 특별공급-이전기관 세대수
    @JsonProperty
    private Integer YGMN_HSHLDCO; // 특별공급-청년 세대수
    @JsonProperty
    private Integer NWBB_HSHLDCO; // 특별공급-신생아 세대수
    @JsonProperty
    @JsonDeserialize(using = CustomLongDeserializer.class)
    private Long LTTOT_TOP_AMOUNT; // 공급금액 (분양최고금액) (단위:만원)

    public HousingAnnouncementModel toEntity() {
        return HousingAnnouncementModel.builder()
                .houseManageNo(HOUSE_MANAGE_NO)
                .pblancNo(PBLANC_NO)
                .modelNo(MODEL_NO)
                .houseTy(HOUSE_TY)
                .suplyAr(SUPLY_AR)
                .suplyHshldco(SUPLY_HSHLDCO)
                .spsplyHshldco(SPSPLY_HSHLDCO)
                .mnychHshldco(MNYCH_HSHLDCO)
                .nwwdsHshldco(NWWDS_HSHLDCO)
                .lfeFrstHshldco(LFE_FRST_HSHLDCO)
                .oldParntsSuportHshldco(OLD_PARNTS_SUPORT_HSHLDCO)
                .insttRecomendHshldco(INSTT_RECOMEND_HSHLDCO)
                .etcHshldco(ETC_HSHLDCO)
                .transrInsttEnfsnHshldco(TRANSR_INSTT_ENFSN_HSHLDCO)
                .ygmnHshldco(YGMN_HSHLDCO)
                .nwbbHshldco(NWBB_HSHLDCO)
                .lttotTopAmount(LTTOT_TOP_AMOUNT != null ? LTTOT_TOP_AMOUNT.doubleValue() : null)
                .build();
    }
}
