package com.cnco.campusflow.banner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

public interface CustomBannerRepository {
    Page<BannerResponseDto> findBanners(
        Long storeId,
        String bannerTypeCd,
        Pageable pageable
    );
}
