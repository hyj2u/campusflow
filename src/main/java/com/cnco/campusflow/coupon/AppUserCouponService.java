package com.cnco.campusflow.coupon;

import com.cnco.campusflow.coupon.AppUserCouponRegisterRequestDto;
import com.cnco.campusflow.coupon.AppUserCouponResponseDto;
import com.cnco.campusflow.coupon.AppUserCouponUseRequestDto;
import com.cnco.campusflow.user.AppUserEntity;
import com.cnco.campusflow.user.AppUserRepository;
import com.cnco.campusflow.coupon.CouponEntity;
import com.cnco.campusflow.coupon.CouponRepository;
import com.cnco.campusflow.coupon.CouponGenEntity;
import com.cnco.campusflow.coupon.CouponGenRepository;
import com.cnco.campusflow.common.PaginatedResponse;
import com.cnco.campusflow.money.AppUserMoneyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppUserCouponService {
    private final AppUserCouponRepository appUserCouponRepository;
    private final CouponRepository couponRepository;
    private final CouponGenRepository couponGenRepository;
    private final AppUserMoneyService appUserMoneyService;
    private final AppUserRepository appUserRepository;

    @Transactional(readOnly = true)
    public PaginatedResponse<AppUserCouponResponseDto> getCouponList(AppUserEntity appUser, String activeYn, String useYn, Pageable pageable) {
        Page<AppUserCouponResponseDto> page = appUserCouponRepository.findAppUserCouponList(appUser, activeYn, useYn, pageable);
        
        return new PaginatedResponse<>(
            page.getContent(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages()
        );
    }

    @Transactional
    public AppUserCouponResponseDto registerCoupon(AppUserEntity appUser, AppUserCouponRegisterRequestDto requestDto) {
        AppUserCouponEntity coupon = appUserCouponRepository.findByCouponNumberAndActiveYnAndAppUserIsNullAndEndDateGreaterThanEqual(
            requestDto.getCouponNumber(), "Y", LocalDateTime.now()
        ).orElseThrow(() -> new IllegalArgumentException("등록할 수 없는 쿠폰입니다."));

        coupon.setAppUser(appUser);
        coupon.setRegiDate(LocalDateTime.now());
        return convertToDto(appUserCouponRepository.save(coupon));
    }

    @Transactional
    public AppUserCouponResponseDto useCoupon(AppUserEntity appUser, Long appUserCouponId) {
        AppUserCouponEntity coupon = appUserCouponRepository.findById(appUserCouponId)
            .orElseThrow(() -> new IllegalArgumentException("쿠폰을 찾을 수 없습니다."));

        // 본인 쿠폰인지 확인
        if (coupon.getAppUser() == null || !appUser.getAppUserId().equals(coupon.getAppUser().getAppUserId())) {
            throw new IllegalArgumentException("본인의 쿠폰만 사용할 수 있습니다.");
        }

        // 사용 가능한 쿠폰인지 확인
        if (!"Y".equals(coupon.getActiveYn()) || "Y".equals(coupon.getUseYn())) {
            throw new IllegalArgumentException("사용할 수 없는 쿠폰입니다.");
        }

        // 만료일 체크
        LocalDateTime endDateTime = getEndDateTime(coupon);
        if (endDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("만료된 쿠폰입니다.");
        }

        // 쿠폰 사용 처리
        coupon.setUseYn("Y");
        appUserCouponRepository.save(coupon);

        // 머니 선물 처리
        AppUserEntity sender = appUserRepository.findById(1L)
            .orElseThrow(() -> new IllegalArgumentException("시스템 사용자를 찾을 수 없습니다."));
            
        appUserMoneyService.giftMoney(
            sender,  // 보내는 사람 (시스템 사용자)
            appUser,  // 받는 사람 (쿠폰 사용자)
            coupon.getCouponAmount().longValue(),  // 쿠폰 금액
            "쿠폰 적용: " + getCouponName(coupon)  // 메모
        );

        return convertToDto(coupon);
    }

    private LocalDateTime getEndDateTime(AppUserCouponEntity coupon) {
        if (coupon.getCoupon() != null) {
            return coupon.getCoupon().getEndDate();
        } else if (coupon.getCouponGen() != null) {
            LocalDateTime endDate = coupon.getCouponGen().getEndDate();
            return endDate;
        }
        throw new IllegalArgumentException("쿠폰 정보가 없습니다.");
    }

    private LocalDateTime getEndDate(AppUserCouponEntity coupon) {
        if (coupon.getCoupon() != null) {
            return coupon.getCoupon().getEndDate();
        } else if (coupon.getCouponGen() != null) {
            LocalDateTime endDate = coupon.getCouponGen().getEndDate();
            return endDate;
        }
        throw new IllegalArgumentException("쿠폰 정보가 없습니다.");
    }

    private String getCouponName(AppUserCouponEntity entity) {
        if (entity.getCoupon() != null) {
            return entity.getCoupon().getCouponName();
        } else if (entity.getCouponGen() != null) {
            return entity.getCouponGen().getCouponName();
        }
        return null;
    }

    private AppUserCouponResponseDto convertToDto(AppUserCouponEntity entity) {
        return new AppUserCouponResponseDto(
            entity.getAppUserCouponId(),
            entity.getCoupon() != null ? entity.getCoupon().getCouponId() : null,
            entity.getCouponGen() != null ? entity.getCouponGen().getCouponGenId() : null,
            entity.getCouponNumber(),
            getCouponName(entity),
            entity.getCouponAmount(),
            getEndDate(entity),
            entity.getUseYn(),
            entity.getActiveYn(),
            entity.getRegiDate()
        );
    }
} 