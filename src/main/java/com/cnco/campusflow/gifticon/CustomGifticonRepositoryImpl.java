package com.cnco.campusflow.gifticon;

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

import java.util.ArrayList;
import java.util.List;

import static com.cnco.campusflow.gifticon.QAppUserGifticonEntity.appUserGifticonEntity;
import static com.cnco.campusflow.gifticon.QGifticonEntity.gifticonEntity;
import static com.cnco.campusflow.product.QProductEntity.productEntity;
import static com.cnco.campusflow.store.QStoreEntity.storeEntity;
import static com.cnco.campusflow.user.QAppUserEntity.appUserEntity;

@Repository
@RequiredArgsConstructor
public class CustomGifticonRepositoryImpl implements CustomGifticonRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<AppUserGifticonResponseDto> findAppUserGifticonList(AppUserEntity appUser, String useYn, Long storeId, String type, Pageable pageable) {
        // 1. gifticon이 있는 경우 조회
        List<AppUserGifticonResponseDto> withGifticon = queryFactory
                .select(Projections.constructor(AppUserGifticonResponseDto.class,
                    appUserGifticonEntity.appUserGifticonId,
                    appUserGifticonEntity.registerDate,
                    appUserGifticonEntity.useYn,
                    gifticonEntity.endDate,
                    gifticonEntity.activeYn,
                    productEntity.productId,
                    productEntity.productNm,
                    storeEntity.storeId,
                    storeEntity.storeNm,
                    storeEntity.storeStatus,
                    appUserEntity.appUserId,
                    appUserEntity.nickname,
                    appUserGifticonEntity.purchaseAmount
                ))
                .from(appUserGifticonEntity)
                .leftJoin(appUserGifticonEntity.sender, appUserEntity)
                .leftJoin(appUserGifticonEntity.gifticon, gifticonEntity)
                .leftJoin(gifticonEntity.product, productEntity)
                .leftJoin(productEntity.store, storeEntity)
                .where(
                    appUserGifticonEntity.receiver.appUserId.eq(appUser.getAppUserId()),
                    appUserGifticonEntity.gifticon.isNotNull(),
                    typeEq(type),
                    appUserGifticonEntity.activeYn.eq("Y"),
                    useYnEq(useYn),
                    storeIdEq(storeId)
                )
                .orderBy(appUserGifticonEntity.appUserGifticonId.desc())
                .fetch();

        // 2. gifticon이 없는 경우 조회
        List<AppUserGifticonResponseDto> withoutGifticon = queryFactory
                .select(Projections.constructor(AppUserGifticonResponseDto.class,
                    appUserGifticonEntity.appUserGifticonId,
                    appUserGifticonEntity.registerDate,
                    appUserGifticonEntity.useYn,
                    appUserGifticonEntity.endDate,
                    appUserGifticonEntity.activeYn,
                    productEntity.productId,
                    productEntity.productNm,
                    storeEntity.storeId,
                    storeEntity.storeNm,
                    storeEntity.storeStatus,
                    appUserEntity.appUserId,
                    appUserEntity.nickname,
                    appUserGifticonEntity.purchaseAmount
                ))
                .from(appUserGifticonEntity)
                .leftJoin(appUserGifticonEntity.sender, appUserEntity)
                .leftJoin(appUserGifticonEntity.product, productEntity)
                .leftJoin(productEntity.store, storeEntity)
                .where(
                    appUserGifticonEntity.receiver.appUserId.eq(appUser.getAppUserId()),
                    appUserGifticonEntity.gifticon.isNull(),
                    typeEq(type),
                    appUserGifticonEntity.activeYn.eq("Y"),
                    useYnEq(useYn),
                    storeIdEq(storeId)
                )
                .orderBy(appUserGifticonEntity.appUserGifticonId.desc())
                .fetch();

        // 3. 결과 합치기
        List<AppUserGifticonResponseDto> combinedList = new ArrayList<>();
        combinedList.addAll(withGifticon);
        combinedList.addAll(withoutGifticon);

        // 4. 페이징 처리
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), combinedList.size());
        List<AppUserGifticonResponseDto> pagedList = start < combinedList.size() ? 
            combinedList.subList(start, end) : new ArrayList<>();

        // 5. 전체 카운트 조회
        Long total = queryFactory
                .select(appUserGifticonEntity.count())
                .from(appUserGifticonEntity)
                .where(
                    appUserGifticonEntity.receiver.appUserId.eq(appUser.getAppUserId()),
                    typeEq(type),
                    appUserGifticonEntity.activeYn.eq("Y"),
                    useYnEq(useYn),
                    storeIdEq(storeId)
                )
                .fetchOne();

        return new PageImpl<>(pagedList, pageable, total);
    }

    private BooleanExpression typeEq(String type) {
        if (!StringUtils.hasText(type)) {
            return null;
        }
        if ("GIFT".equals(type)) {
            return appUserGifticonEntity.type.eq("GIFT");
        }
        if ("PURCHASE".equals(type)) {
            return appUserGifticonEntity.type.eq("PURCHASE");
        }
        return null;
    }

    private BooleanExpression useYnEq(String useYn) {
        if (!StringUtils.hasText(useYn)) {
            return null;
        }
        if ("Y".equals(useYn)) {
            return appUserGifticonEntity.useYn.eq("Y");
        }
        if ("N".equals(useYn)) {
            return appUserGifticonEntity.useYn.eq("N");
        }
        return null;
    }

    private BooleanExpression storeIdEq(Long storeId) {
        if (storeId == null) {
            return null;
        }
        
        return appUserGifticonEntity.product.store.storeId.eq(storeId);
    }
} 