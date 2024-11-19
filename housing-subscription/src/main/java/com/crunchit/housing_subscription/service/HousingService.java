package com.crunchit.housing_subscription.service;


import com.crunchit.housing_subscription.dto.response.*;
import com.crunchit.housing_subscription.entity.HousingAnnouncement;
import com.crunchit.housing_subscription.entity.HousingAnnouncementId;
import com.crunchit.housing_subscription.entity.HousingAnnouncementModel;
import com.crunchit.housing_subscription.entity.UserSubscriptionLike;
import com.crunchit.housing_subscription.repository.HousingAnnouncementRepository;
import com.crunchit.housing_subscription.repository.UserSubscriptionLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HousingService {
    private final HousingAnnouncementRepository housingRepository;
    private final UserSubscriptionLikeRepository housingLikeRepository;

    @Transactional(readOnly = true)
    public HousingResponseDto getHousingAnnouncements(int page, int pageSize, Long userId){
        PageRequest pageRequest = PageRequest.of(page-1, pageSize, Sort.by(Sort.Direction.DESC, "rcritPblancDe"));
        Page<HousingAnnouncement> entityPage = housingRepository.findAll(pageRequest);
        List<UserSubscriptionLike> likedAnnouncements = housingLikeRepository.findAllByUser_UserId(userId);
        Set<HousingAnnouncementId> announcementIdSet = new HashSet<>();
        for(UserSubscriptionLike likedAnnouncement : likedAnnouncements){
            HousingAnnouncementId likedId = new HousingAnnouncementId(likedAnnouncement.getHousingAnnouncement().getPblancNo(), likedAnnouncement.getHousingAnnouncement().getHouseManageNo());
            announcementIdSet.add(likedId);
        }
        return getHousingResponseDto(entityPage, announcementIdSet);
    }

    @Transactional(readOnly = true)
    public HousingResponseDto getHousingAnnouncementsLike (int page, int pageSize, Long userId){
        PageRequest pageRequest = PageRequest.of(page-1, pageSize, Sort.by(Sort.Direction.DESC, "rcrit_pblanc_de"));
        Page<HousingAnnouncement> entityPage = housingRepository.findAllWithLikes(userId, pageRequest);
        List<UserSubscriptionLike> likedAnnouncements = housingLikeRepository.findAllByUser_UserId(userId);
        Set<HousingAnnouncementId> announcementIdSet = new HashSet<>();
        for(UserSubscriptionLike likedAnnouncement : likedAnnouncements){
            HousingAnnouncementId likedId = new HousingAnnouncementId(likedAnnouncement.getHousingAnnouncement().getPblancNo(), likedAnnouncement.getHousingAnnouncement().getHouseManageNo());
            announcementIdSet.add(likedId);
        }
        return getHousingResponseDto(entityPage, announcementIdSet);
    }


    private HousingResponseDto getHousingResponseDto(Page<HousingAnnouncement> entityPage, Set<HousingAnnouncementId> announcementIdSet) {
        Page<HousingListDto> dtoPage =  entityPage.map(a-> HousingListDto.builder()
                .house_manage_no(a.getHouseManageNo())
                .pblanc_no(a.getPblancNo())
                .house_nm(a.getHouseNm())
                .house_secd(a.getHouseSecd())
                .house_secd_nm(a.getHouseSecdNm())
                .house_dtl_secd(a.getHouseDtlSecd())
                .rent_secd(a.getRentSecd())
                .rent_secd_nm(a.getRentSecdNm())
                .subscrpt_area_code_nm(a.getSubscrptAreaCodeNm())
                .tot_suply_hshldco(a.getTotSuplyHshldco())
                .hssply_adres(a.getHssplyAdres())
                .rcrit_pblanc_de(formatDate(a.getRcritPblancDe()))
                .subscrpt_rcept_bgnde(formatDate(a.getSubscrptRceptBgnde()))
                .subscrpt_rcept_endde(formatDate(a.getSubscrptRceptEndde()))
                .rcept_bgnde(formatDate(a.getRceptBgnde()))
                .rcept_endde(formatDate(a.getRceptEndde()))
                .isLiked(checkLiked(announcementIdSet, a))
                .build());

        return HousingResponseDto.builder()
                .totalPages(dtoPage.getTotalPages())
                .page(dtoPage.getNumber()+1)
                .totalItems(dtoPage.getTotalElements())
                .data(dtoPage.getContent())
                .pageSize(dtoPage.getSize())
                .build();
    }
    @Transactional(readOnly = true)
    public HousingMonthlyResponseDto getHousingMonthlyAnnouncements(Integer year, Integer month, Long userId){
        Date firstDay = getFirstDayOfMonth(year, month);
        Date lastDay = getLastDayOfMonth(year, month);
        List<HousingAnnouncement> announcements = housingRepository.findByDateRange(firstDay, lastDay);
        List<UserSubscriptionLike> likedAnnouncements = housingLikeRepository.findAllByUser_UserId(userId);
        Set<HousingAnnouncementId> announcementIdSet = new HashSet<>();
        for(UserSubscriptionLike likedAnnouncement : likedAnnouncements){
            HousingAnnouncementId likedId = new HousingAnnouncementId(likedAnnouncement.getHousingAnnouncement().getPblancNo(), likedAnnouncement.getHousingAnnouncement().getHouseManageNo());
            announcementIdSet.add(likedId);
        }
        return getHousingMonthlyResponseDto(year, month, announcements, announcementIdSet);
    }
    @Transactional(readOnly = true)
    public HousingMonthlyResponseDto getHousingMonthlyAnnouncementsLike(Integer year, Integer month, Long userId){
        Date firstDay = getFirstDayOfMonth(year, month);
        Date lastDay = getLastDayOfMonth(year, month);
        List<HousingAnnouncement> announcements = housingRepository.findByDateRangeLike(firstDay, lastDay, userId);
        List<UserSubscriptionLike> likedAnnouncements = housingLikeRepository.findAllByUser_UserId(userId);
        Set<HousingAnnouncementId> announcementIdSet = new HashSet<>();
        for(UserSubscriptionLike likedAnnouncement : likedAnnouncements){
            HousingAnnouncementId likedId = new HousingAnnouncementId(likedAnnouncement.getHousingAnnouncement().getPblancNo(), likedAnnouncement.getHousingAnnouncement().getHouseManageNo());
            announcementIdSet.add(likedId);
        }
        return getHousingMonthlyResponseDto(year, month, announcements, announcementIdSet);
    }

    private HousingMonthlyResponseDto getHousingMonthlyResponseDto(Integer year, Integer month, List<HousingAnnouncement> announcements, Set<HousingAnnouncementId> idSet) {
        List<HousingMonthlyDto> announcementDtoList = announcements.stream().map(
                a-> HousingMonthlyDto.builder()
                        .house_manage_no(a.getHouseManageNo())
                        .pblanc_no(a.getPblancNo())
                        .house_nm(a.getHouseNm())
                        .house_secd(a.getHouseSecd())
                        .house_secd_nm(a.getHouseSecdNm())
                        .house_dtl_secd(a.getHouseDtlSecd())
                        .house_dtl_secd_nm(a.getHouseDtlSecdNm())
                        .subscrpt_area_code_nm(a.getSubscrptAreaCodeNm())
                        .hssply_adres(a.getHssplyAdres())
                        .tot_suply_hshldco(a.getTotSuplyHshldco())
                        .rcrit_pblanc_de(formatDate(a.getRcritPblancDe()))
                        .subscrpt_rcept_bgnde(formatDate(a.getSubscrptRceptBgnde()))
                        .subscrpt_rcept_endde(formatDate(a.getSubscrptRceptEndde()))
                        .rcept_bgnde(formatDate(a.getRceptBgnde()))
                        .rcept_endde(formatDate(a.getRceptEndde()))
                        .spsply_rcept_bgnde(formatDate(a.getSpsplyRceptBgnde()))
                        .spsply_rcept_endde(formatDate(a.getSpsplyRceptEndde()))
                        .gnrl_rnk1_crsparea_rcptde(formatDate(a.getGnrlRnk1CrspareaRcptde()))
                        .gnrl_rnk1_crsparea_endde(formatDate(a.getGnrlRnk1CrspareaEndde()))
                        .gnrl_rnk1_etc_gg_rcptde(formatDate(a.getGnrlRnk1EtcGgRcptde()))
                        .gnrl_rnk1_etc_gg_endde(formatDate(a.getGnrlRnk1EtcGgEndde()))
                        .gnrl_rnk1_etc_area_rcptde(formatDate(a.getGnrlRnk1EtcAreaRcptde()))
                        .gnrl_rnk1_etc_area_endde(formatDate(a.getGnrlRnk1CrspareaEndde()))
                        .gnrl_rnk2_crsparea_rcptde(formatDate(a.getGnrlRnk2CrspareaRcptde()))
                        .gnrl_rnk2_crsparea_endde(formatDate(a.getGnrlRnk2CrspareaEndde()))
                        .gnrl_rnk2_etc_gg_rcptde(formatDate(a.getGnrlRnk2EtcGgRcptde()))
                        .gnrl_rnk2_etc_gg_endde(formatDate(a.getGnrlRnk2EtcGgEndde()))
                        .gnrl_rnk2_etc_area_rcptde(formatDate(a.getGnrlRnk2EtcAreaRcptde()))
                        .gnrl_rnk2_etc_area_endde(formatDate(a.getGnrlRnk2CrspareaEndde()))
                        .isLiked(checkLiked(idSet, a))
                        .build()
        ).toList();
        return HousingMonthlyResponseDto.builder()
                .year(year)
                .month(month)
                .totalItems(announcementDtoList.size())
                .data(announcementDtoList)
                .build();
    }

    @Transactional(readOnly = true)
    public HousingMappedResponseDto getHousingMappedAnnouncements(Long userId){
        List<HousingAnnouncement> announcements = housingRepository.findByEndDatesAfterNow();
        List<UserSubscriptionLike> likedAnnouncements = housingLikeRepository.findAllByUser_UserId(userId);
        Set<HousingAnnouncementId> announcementIdSet = new HashSet<>();
        for(UserSubscriptionLike likedAnnouncement : likedAnnouncements){
            HousingAnnouncementId likedId = new HousingAnnouncementId(likedAnnouncement.getHousingAnnouncement().getPblancNo(), likedAnnouncement.getHousingAnnouncement().getHouseManageNo());
            announcementIdSet.add(likedId);
        }
        return getHousingMappedResponseDto(announcements, announcementIdSet);
    }

    @Transactional(readOnly = true)
    public HousingMappedResponseDto getHousingMappedAnnouncementsLike(Long userId){
        List<HousingAnnouncement> announcements = housingRepository.findByEndDatesAfterNowLike(userId);
        List<UserSubscriptionLike> likedAnnouncements = housingLikeRepository.findAllByUser_UserId(userId);
        Set<HousingAnnouncementId> announcementIdSet = new HashSet<>();
        for(UserSubscriptionLike likedAnnouncement : likedAnnouncements){
            HousingAnnouncementId likedId = new HousingAnnouncementId(likedAnnouncement.getHousingAnnouncement().getPblancNo(), likedAnnouncement.getHousingAnnouncement().getHouseManageNo());
            announcementIdSet.add(likedId);
        }
        return getHousingMappedResponseDto(announcements, announcementIdSet);
    }

    private HousingMappedResponseDto getHousingMappedResponseDto(List<HousingAnnouncement> announcements, Set<HousingAnnouncementId> idSet) {
        List<HousingMappedDto> housingMappedDtoList = announcements.stream().map(
                a-> HousingMappedDto.builder()
                        .house_manage_no(a.getHouseManageNo())
                        .pblanc_no(a.getPblancNo())
                        .house_nm(a.getHouseNm())
                        .house_secd(a.getHouseSecd())
                        .house_secd_nm(a.getHouseSecdNm())
                        .house_dtl_secd(a.getHouseDtlSecd())
                        .rent_secd(a.getRentSecd())
                        .rent_secd_nm(a.getRentSecdNm())
                        .subscrpt_area_code_nm(a.getSubscrptAreaCodeNm())
                        .tot_suply_hshldco(a.getTotSuplyHshldco())
                        .hssply_adres(a.getHssplyAdres())
                        .hssply_zip(a.getHssplyZip())
                        .rcrit_pblanc_de(formatDate(a.getRcritPblancDe()))
                        .subscrpt_rcept_bgnde(formatDate(a.getSubscrptRceptBgnde()))
                        .subscrpt_rcept_endde(formatDate(a.getSubscrptRceptEndde()))
                        .rcept_bgnde(formatDate(a.getRceptBgnde()))
                        .rcept_endde(formatDate(a.getRceptEndde()))
                        .isLiked(checkLiked(idSet, a))
                        .build()
        ).toList();

        return HousingMappedResponseDto.builder()
                .totalItems(housingMappedDtoList.size())
                .data(housingMappedDtoList)
                .build();
    }


    public HousingDetailResponseDto getHousingAnnouncementDetail(String pblancNo, String houseManageNo, Long userId){
        HousingAnnouncementId housingAnnouncementId = new HousingAnnouncementId();
        housingAnnouncementId.setPblancNo(pblancNo);
        housingAnnouncementId.setHouseManageNo(houseManageNo);
        HousingAnnouncement announcement = housingRepository.findById(housingAnnouncementId).orElseThrow(IllegalArgumentException::new);
        List<UserSubscriptionLike> likedAnnouncements = housingLikeRepository.findAllByUser_UserId(userId);
        Set<HousingAnnouncementId> announcementIdSet = new HashSet<>();
        for(UserSubscriptionLike likedAnnouncement : likedAnnouncements){
            HousingAnnouncementId likedId = new HousingAnnouncementId(likedAnnouncement.getHousingAnnouncement().getPblancNo(), likedAnnouncement.getHousingAnnouncement().getHouseManageNo());
            announcementIdSet.add(likedId);
        }
        List<HousingModelDto> modelDtoList = announcement.getModels().stream().map(model->
            HousingModelDto.builder()
                    .house_manage_no(model.getHouseManageNo())
                    .pblanc_no(model.getPblancNo())
                    .house_ty(model.getHouseTy())
                    .suply_ar(model.getSuplyAr())
                    .suply_hshldco(model.getSuplyHshldco())
                    .spsply_hshldco(model.getSpsplyHshldco())
                    .mnych_hshldco(model.getMnychHshldco())
                    .nwwds_hshldco(model.getNwwdsHshldco())
                    .lfe_frst_hshldco(model.getLfeFrstHshldco())
                    .old_parnts_suport_hshldco(model.getOldParntsSuportHshldco())
                    .instt_recomend_hshldco(model.getInsttRecomendHshldco())
                    .etc_hshldco(model.getEtcHshldco())
                    .transr_instt_enfsn_hshldco(model.getTransrInsttEnfsnHshldco())
                    .ygmn_hshldco(model.getYgmnHshldco())
                    .nwbb_hshldco(model.getNwbbHshldco())
                    .lttot_top_amount(model.getLttotTopAmount())
                    .build()
        ).toList();
        HousingAnnouncementDto announcementDto = HousingAnnouncementDto.builder()
                .house_manage_no(announcement.getHouseManageNo())
                .pblanc_no(announcement.getPblancNo())
                .house_nm(announcement.getHouseNm())
                .house_secd(announcement.getHouseSecd())
                .house_secd_nm(announcement.getHouseSecdNm())
                .house_dtl_secd(announcement.getHouseDtlSecd())
                .house_dtl_secd_nm(announcement.getHouseDtlSecdNm())
                .rent_secd(announcement.getRentSecd())
                .rent_secd_nm(announcement.getRentSecdNm())
                .subscrpt_area_code(announcement.getSubscrptAreaCode())
                .subscrpt_area_code_nm(announcement.getSubscrptAreaCodeNm())
                .hssply_zip(announcement.getHssplyZip())
                .hssply_adres(announcement.getHssplyAdres())
                .tot_suply_hshldco(announcement.getTotSuplyHshldco())
                .rcrit_pblanc_de(announcement.getRcritPblancDe() != null ? announcement.getRcritPblancDe().toString() : null)
                .subscrpt_rcept_bgnde(announcement.getSubscrptRceptBgnde() != null ? announcement.getSubscrptRceptBgnde().toString() : null)
                .subscrpt_rcept_endde(announcement.getSubscrptRceptEndde() != null ? announcement.getSubscrptRceptEndde().toString() : null)
                .rcept_bgnde(announcement.getRceptBgnde() != null ? announcement.getRceptBgnde().toString() : null)
                .rcept_endde(announcement.getRceptEndde() != null ? announcement.getRceptEndde().toString() : null)
                .spsply_rcept_bgnde(announcement.getSpsplyRceptBgnde() != null ? announcement.getSpsplyRceptBgnde().toString() : null)
                .spsply_rcept_endde(announcement.getSpsplyRceptEndde() != null ? announcement.getSpsplyRceptEndde().toString() : null)
                .gnrl_rnk1_crsparea_rcptde(announcement.getGnrlRnk1CrspareaRcptde() != null ? announcement.getGnrlRnk1CrspareaRcptde().toString() : null)
                .gnrl_rnk1_crsparea_endde(announcement.getGnrlRnk1CrspareaEndde() != null ? announcement.getGnrlRnk1CrspareaEndde().toString() : null)
                .gnrl_rnk1_etc_gg_rcptde(announcement.getGnrlRnk1EtcGgRcptde() != null ? announcement.getGnrlRnk1EtcGgRcptde().toString() : null)
                .gnrl_rnk1_etc_gg_endde(announcement.getGnrlRnk1EtcGgEndde() != null ? announcement.getGnrlRnk1EtcGgEndde().toString() : null)
                .gnrl_rnk1_etc_area_rcptde(announcement.getGnrlRnk1EtcAreaRcptde() != null ? announcement.getGnrlRnk1EtcAreaRcptde().toString() : null)
                .gnrl_rnk1_etc_area_endde(announcement.getGnrlRnk1EtcAreaEndde() != null ? announcement.getGnrlRnk1EtcAreaEndde().toString() : null)
                .gnrl_rnk2_crsparea_rcptde(announcement.getGnrlRnk2CrspareaRcptde() != null ? announcement.getGnrlRnk2CrspareaRcptde().toString() : null)
                .gnrl_rnk2_crsparea_endde(announcement.getGnrlRnk2CrspareaEndde() != null ? announcement.getGnrlRnk2CrspareaEndde().toString() : null)
                .gnrl_rnk2_etc_gg_rcptde(announcement.getGnrlRnk2EtcGgRcptde() != null ? announcement.getGnrlRnk2EtcGgRcptde().toString() : null)
                .gnrl_rnk2_etc_gg_endde(announcement.getGnrlRnk2EtcGgEndde() != null ? announcement.getGnrlRnk2EtcGgEndde().toString() : null)
                .gnrl_rnk2_etc_area_rcptde(announcement.getGnrlRnk2EtcAreaRcptde() != null ? announcement.getGnrlRnk2EtcAreaRcptde().toString() : null)
                .gnrl_rnk2_etc_area_endde(announcement.getGnrlRnk2EtcAreaEndde() != null ? announcement.getGnrlRnk2EtcAreaEndde().toString() : null)
                .przwner_presnatn_de(announcement.getPrzwnerPresnatnDe() != null ? announcement.getPrzwnerPresnatnDe().toString() : null)
                .cntrct_cncls_bgnde(announcement.getCntrctCnclsBgnde() != null ? announcement.getCntrctCnclsBgnde().toString() : null)
                .cntrct_cncls_endde(announcement.getCntrctCnclsEndde() != null ? announcement.getCntrctCnclsEndde().toString() : null)
                .hmpg_adres(announcement.getHmpgAdres())
                .cnstrct_entrps_nm(announcement.getCnstrctEntrpsNm())
                .mdhs_telno(announcement.getMdhsTelno())
                .bsns_mby_nm(announcement.getBsnsMbyNm())
                .mvn_prearnge_yn(announcement.getMvnPrearngeYn())
                .speclt_rdn_earth_at(announcement.getSpecltRdnEarthAt())
                .mdat_trget_area_secd(announcement.getMdatTrgetAreaSecd())
                .parcprc_uls_at(announcement.getParcprcUlsAt())
                .imprmn_bsns_at(announcement.getImprmnBsnsAt())
                .public_house_earth_at(announcement.getPublicHouseEarthAt())
                .lrscl_bldlnd_at(announcement.getLrsclBldlndAt())
                .npln_prvopr_public_house_at(announcement.getNplnPrvoprPublicHouseAt())
                .public_house_spclm_applc_apt(announcement.getPublicHouseSpclmApplcApt())
                .pblanc_url(announcement.getPblancUrl())
                .isLiked(checkLiked(announcementIdSet, announcement))
                .house_models(modelDtoList)
                .build();

        return HousingDetailResponseDto.builder()
                .data(announcementDto)
                .build();
    }

    private boolean checkLiked(Set<HousingAnnouncementId> idSet, HousingAnnouncement a){
        HousingAnnouncementId id = new HousingAnnouncementId(a.getPblancNo(), a.getHouseManageNo());
        return idSet.contains(id);
    }

    private String formatDate(Date date){
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    private Date getFirstDayOfMonth(int year, int month) {
        // 연월의 첫 번째 날
        LocalDate firstDay = LocalDate.of(year, month, 1);
        return convertToDate(firstDay);
    }

    private Date getLastDayOfMonth(int year, int month) {
        // 연월의 마지막 날
        LocalDate lastDate = LocalDate.of(year, month, 1).withDayOfMonth(LocalDate.of(year, month, 1).lengthOfMonth()); // 마지막 날
        return convertToDate(lastDate);
    }

    private Date convertToDate(LocalDate localDate) {

        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
