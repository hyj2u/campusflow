package com.cnco.campusflow.userconf;

import com.cnco.campusflow.user.AppUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppUserconfNotiService {
    
    private final AppUserconfNotiRepository appUserconfNotiRepository;

    /**
     * 사용자의 알림 설정 정보를 조회합니다.
     * 
     * @param appUser 조회할 사용자 정보
     * @return 사용자의 알림 설정 정보
     */
    public AppUserconfNotiEntity getUserNotiConfig(AppUserEntity appUser) {
        return appUserconfNotiRepository.findByAppUser(appUser)
                .orElseGet(() -> AppUserconfNotiEntity.builder()
                        .appUser(appUser)
                        .build());
    }

    /**
     * 사용자의 알림 설정을 생성하거나 업데이트합니다.
     * 
     * @param appUser 사용자 정보
     * @param requestEntity 알림 설정 정보
     * @return 생성/수정된 알림 설정 정보
     */
    @Transactional
    public AppUserconfNotiEntity createOrUpdateNotiConfig(AppUserEntity appUser, AppUserconfNotiEntity requestEntity) {
        AppUserconfNotiEntity notiConfig = appUserconfNotiRepository.findByAppUser(appUser)
                .orElseGet(() -> AppUserconfNotiEntity.builder()
                        .appUser(appUser)
                        .build());

        // 각 필드가 null이 아닌 경우에만 업데이트
        if (requestEntity.getLectureNotiYn() != null) {
            notiConfig.setLectureNotiYn(requestEntity.getLectureNotiYn());
        }
        if (requestEntity.getOrderNotiYn() != null) {
            notiConfig.setOrderNotiYn(requestEntity.getOrderNotiYn());
        }
        if (requestEntity.getMembershipNotiYn() != null) {
            notiConfig.setMembershipNotiYn(requestEntity.getMembershipNotiYn());
        }
        if (requestEntity.getDoNotDisturbYn() != null) {
            notiConfig.setDoNotDisturbYn(requestEntity.getDoNotDisturbYn());
        }
        if (requestEntity.getDoNotDisturbStartTime() != null) {
            notiConfig.setDoNotDisturbStartTime(requestEntity.getDoNotDisturbStartTime());
        }
        if (requestEntity.getDoNotDisturbEndTime() != null) {
            notiConfig.setDoNotDisturbEndTime(requestEntity.getDoNotDisturbEndTime());
        }
        if (requestEntity.getNewsNotiYn() != null) {
            notiConfig.setNewsNotiYn(requestEntity.getNewsNotiYn());
        }
        if (requestEntity.getMarketingYn() != null) {
            notiConfig.setMarketingYn(requestEntity.getMarketingYn());
        }
        if (requestEntity.getMarketingAgreeTimestamp() != null) {
            notiConfig.setMarketingAgreeTimestamp(requestEntity.getMarketingAgreeTimestamp());
        }
        if (requestEntity.getBoardStopNotiYn() != null) {
            notiConfig.setBoardStopNotiYn(requestEntity.getBoardStopNotiYn());
        }
        if (requestEntity.getAnswerNotiYn() != null) {
            notiConfig.setAnswerNotiYn(requestEntity.getAnswerNotiYn());
        }
        if (requestEntity.getReplyNotiYn() != null) {
            notiConfig.setReplyNotiYn(requestEntity.getReplyNotiYn());
        }

        return appUserconfNotiRepository.save(notiConfig);
    }
} 