package com.cnco.campusflow.coupon;

import com.cnco.campusflow.user.AppUserEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static com.cnco.campusflow.coupon.QAppUserCouponEntity.appUserCouponEntity;
import static com.cnco.campusflow.coupon.QCouponEntity.couponEntity;
import static com.cnco.campusflow.coupon.QCouponGenEntity.couponGenEntity;

@Repository
@RequiredArgsConstructor
public class CustomAppUserCouponRepositoryImpl implements CustomAppUserCouponRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<AppUserCouponEntity> findByCouponNumberAndActiveYnAndAppUserIsNullAndEndDateGreaterThanEqual(
        String couponNumber,
        String activeYn,
        LocalDateTime now
    ) {
        return Optional.ofNullable(queryFactory
            .selectFrom(appUserCouponEntity)
            .leftJoin(appUserCouponEntity.coupon, couponEntity)
            .leftJoin(appUserCouponEntity.couponGen, couponGenEntity)
            .where(
                appUserCouponEntity.couponNumber.eq(couponNumber),
                appUserCouponEntity.activeYn.eq(activeYn),
                appUserCouponEntity.appUser.isNull(),
                endDateGreaterThanEqual(now)
            )
            .fetchFirst());
    }

    @Override
    public Page<AppUserCouponEntity> findByAppUserAndActiveYnAndUseYnAndEndDateGreaterThanEqualOrderByEndDateAsc(
        AppUserEntity appUser,
        String activeYn,
        String useYn,
        LocalDateTime now,
        Pageable pageable
    ) {
        List<AppUserCouponEntity> results = queryFactory
            .selectFrom(appUserCouponEntity)
            .leftJoin(appUserCouponEntity.coupon, couponEntity)
            .leftJoin(appUserCouponEntity.couponGen, couponGenEntity)
            .where(
                appUserCouponEntity.appUser.eq(appUser),
                activeYnEq(activeYn),
                useYnEq(useYn),
                endDateGreaterThanEqual(now)
            )
            .orderBy(
                appUserCouponEntity.coupon.endDate.coalesce(appUserCouponEntity.couponGen.endDate).asc()
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = queryFactory
            .select(appUserCouponEntity.count())
            .from(appUserCouponEntity)
            .leftJoin(appUserCouponEntity.coupon, couponEntity)
            .leftJoin(appUserCouponEntity.couponGen, couponGenEntity)
            .where(
                appUserCouponEntity.appUser.eq(appUser),
                activeYnEq(activeYn),
                useYnEq(useYn),
                endDateGreaterThanEqual(now)
            )
            .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<AppUserCouponResponseDto> findAppUserCouponList(AppUserEntity appUser, String activeYn, String useYn, Pageable pageable) {
        // 1. coupon이 있는 경우 조회
        List<AppUserCouponResponseDto> withCoupon = queryFactory
            .select(Projections.constructor(AppUserCouponResponseDto.class,
                appUserCouponEntity.appUserCouponId,
                appUserCouponEntity.coupon.couponId,
                appUserCouponEntity.couponGen.couponGenId,
                appUserCouponEntity.couponNumber,
                appUserCouponEntity.coupon.couponName,
                appUserCouponEntity.couponAmount,
                appUserCouponEntity.coupon.endDate,
                appUserCouponEntity.useYn,
                appUserCouponEntity.activeYn,
                appUserCouponEntity.regiDate
            ))
            .from(appUserCouponEntity)
            .leftJoin(appUserCouponEntity.coupon, couponEntity)
            .where(
                appUserCouponEntity.appUser.eq(appUser),
                appUserCouponEntity.coupon.isNotNull(),
                activeYnEq(activeYn),
                useYnEq(useYn)
            )
            .orderBy(appUserCouponEntity.coupon.endDate.asc())
            .fetch();

        // 2. couponGen이 있는 경우 조회
        List<AppUserCouponResponseDto> withCouponGen = queryFactory
            .select(Projections.constructor(AppUserCouponResponseDto.class,
                appUserCouponEntity.appUserCouponId,
                appUserCouponEntity.coupon.couponId,
                appUserCouponEntity.couponGen.couponGenId,
                appUserCouponEntity.couponNumber,
                appUserCouponEntity.couponGen.couponName,
                appUserCouponEntity.couponAmount,
                appUserCouponEntity.couponGen.endDate,
                appUserCouponEntity.useYn,
                appUserCouponEntity.activeYn,
                appUserCouponEntity.regiDate
            ))
            .from(appUserCouponEntity)
            .leftJoin(appUserCouponEntity.couponGen, couponGenEntity)
            .where(
                appUserCouponEntity.appUser.eq(appUser),
                appUserCouponEntity.couponGen.isNotNull(),
                activeYnEq(activeYn),
                useYnEq(useYn)
            )
            .orderBy(appUserCouponEntity.couponGen.endDate.asc())
            .fetch();

        // 3. 결과 합치기
        List<AppUserCouponResponseDto> combinedList = new ArrayList<>();
        combinedList.addAll(withCoupon);
        combinedList.addAll(withCouponGen);

        // 4. 페이징 처리
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), combinedList.size());
        List<AppUserCouponResponseDto> pagedList = start < combinedList.size() ? 
            combinedList.subList(start, end) : new ArrayList<>();

        // 5. 전체 카운트 조회
        Long total = queryFactory
            .select(appUserCouponEntity.count())
            .from(appUserCouponEntity)
            .where(
                appUserCouponEntity.appUser.eq(appUser),
                activeYnEq(activeYn),
                useYnEq(useYn)
            )
            .fetchOne();

        return new PageImpl<>(pagedList, pageable, total);
    }

    private BooleanExpression activeYnEq(String activeYn) {
        if (!StringUtils.hasText(activeYn)) {
            return null;
        }
        return appUserCouponEntity.activeYn.eq(activeYn);
    }

    private BooleanExpression useYnEq(String useYn) {
        if (!StringUtils.hasText(useYn)) {
            return null;
        }
        return appUserCouponEntity.useYn.eq(useYn);
    }

    private BooleanExpression endDateGreaterThanEqual(LocalDateTime now) {
        return appUserCouponEntity.coupon.endDate.coalesce(appUserCouponEntity.couponGen.endDate).goe(now);
    }
} 