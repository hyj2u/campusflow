package com.cnco.campusflow.coupon.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class AppUserCouponResponseDto {
    private final Long appUserCouponId;
    private final Long couponId;
    private final Long couponGenId;
    private final String couponNumber;
    private final String couponName;
    private final long amount;
    private final LocalDateTime endDate;
    private final String useYn;
    private final String activeYn;
    private final String registerDate;
} 