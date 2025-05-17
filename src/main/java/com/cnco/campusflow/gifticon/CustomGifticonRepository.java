package com.cnco.campusflow.gifticon;

import com.cnco.campusflow.user.AppUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomGifticonRepository {
    Page<GifticonEntity> findGifticonList(AppUserEntity appUser, String type, String activeYn, Pageable pageable);
} 