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
    private final AppUserGifticonRepository appUserGifticonRepository;

    /**
     * 기프티콘 목록을 조회합니다.
     */
    public Page<AppUserGifticonResponseDto> getGifticonList(AppUserEntity appUser, String activeYn, String type, Pageable pageable) {
        try {
            log.info("기프티콘 목록 조회 시작 - userId: {}, type: {}, activeYn: {}", 
                    appUser.getUserId(), type, activeYn);
            
            Page<AppUserGifticonResponseDto> gifticons = appUserGifticonRepository.findAppUserGifticonList(appUser, activeYn, type, pageable);

            log.info("기프티콘 목록 조회 완료 - 총 {}건", gifticons.getTotalElements());
            return new PageImpl<>(gifticons.getContent(), pageable, gifticons.getTotalElements());
        } catch (Exception e) {
            log.error("기프티콘 목록 조회 중 오류 발생 - 원인: {}", e.getCause() != null ? e.getCause().getMessage() : e.getMessage(), e);
            throw e;
        }
    }
} 