package com.cnco.campusflow.coupon;

import com.cnco.campusflow.user.AppUserEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomAppUserCouponRepository {
    Optional<AppUserCouponEntity> findByCouponNumberAndActiveYnAndAppUserIsNullAndEndDateGreaterThanEqual(
        String couponNumber,
        String activeYn,
        LocalDateTime now
    );

    Page<AppUserCouponEntity> findByAppUserAndActiveYnAndUseYnAndEndDateGreaterThanEqualOrderByEndDateAsc(
        AppUserEntity appUser,
        String activeYn,
        String useYn,
        LocalDateTime now,
        Pageable pageable
    );

    Page<AppUserCouponResponseDto> findAppUserCouponList(AppUserEntity appUser, String activeYn, String useYn, Pageable pageable);
} 