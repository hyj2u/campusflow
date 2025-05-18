package com.cnco.campusflow.gifticon;

import com.cnco.campusflow.user.AppUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomGifticonRepository {
    Page<AppUserGifticonResponseDto> findAppUserGifticonList(AppUserEntity appUser, String activeYn, String type, Pageable pageable);
} 