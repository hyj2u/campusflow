package com.cnco.campusflow.gifticon;

import com.cnco.campusflow.user.AppUserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class GifticonService {
    private final GifticonRepository gifticonRepository;
    private final AppUserGifticonRepository appUserGifticonRepository;

    /**
     * 기프티콘 목록을 조회합니다.
     */
    public Page<GifticonResponseDto> getGifticonList(AppUserEntity appUser, String type, String activeYn, Pageable pageable) {
        log.info("기프티콘 목록 조회 시작 - userId: {}, type: {}, activeYn: {}", 
                appUser.getUserId(), type, activeYn);
        
        // 로그인한 사용자의 기프티콘 목록 조회
        Page<AppUserGifticonEntity> appUserGifticonPage = appUserGifticonRepository
            .findByReceiver_AppUserIdAndActiveYn(appUser.getAppUserId(), activeYn, pageable);
        
        List<GifticonResponseDto> content = appUserGifticonPage.getContent().stream()
                .map(appUserGifticon -> {
                    log.info("AppUserGifticon ID: {}, Sender: {}, Sender ID: {}", 
                            appUserGifticon.getAppUserGifticonId(),
                            appUserGifticon.getSender() != null ? appUserGifticon.getSender().getNickname() : "null",
                            appUserGifticon.getSender() != null ? appUserGifticon.getSender().getAppUserId() : "null");
                    
                    return GifticonResponseDto.from(appUserGifticon.getGifticon(), appUserGifticon);
                })
                .toList();

        log.info("기프티콘 목록 조회 완료 - 총 {}건", appUserGifticonPage.getTotalElements());
        return new PageImpl<>(content, pageable, appUserGifticonPage.getTotalElements());
    }
} 