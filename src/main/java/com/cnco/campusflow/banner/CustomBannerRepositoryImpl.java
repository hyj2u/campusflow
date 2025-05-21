package com.cnco.campusflow.banner;

import com.cnco.campusflow.bannertype.QBannerTypeEntity;
import com.cnco.campusflow.image.QImageEntity;
import com.cnco.campusflow.store.QStoreEntity;
import com.cnco.campusflow.brand.QBrandEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Repository
public class CustomBannerRepositoryImpl implements CustomBannerRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BannerResponseDto> findBanners(
            Long storeId,
            String bannerTypeCd,
            Pageable pageable
    ) {
        QBannerEntity banner = QBannerEntity.bannerEntity;
        QBannerTypeEntity bannerTypeEntity = QBannerTypeEntity.bannerTypeEntity;
        QImageEntity imageEntity = QImageEntity.imageEntity;
        QStoreEntity store = QStoreEntity.storeEntity;
        QBrandEntity brand = QBrandEntity.brandEntity;

        var query = queryFactory
                .select(Projections.fields(
                        BannerResponseDto.class,
                        banner.bannerId.as("bannerId"),
                        banner.bannerNm.as("bannerNm"),
                        banner.bannerUrl.as("bannerUrl"),
                        banner.activeYn.as("activeYn"),
                        banner.bannerStart.as("bannerStart"),
                        banner.bannerEnd.as("bannerEnd"),
                        banner.fullExpYn.as("fullExpYn"),
                        bannerTypeEntity.bannerTypeId.as("bannerTypeId"),
                        imageEntity.imageId.as("imageId"),
                        imageEntity.imgPath.as("imgPath"),
                        banner.viewCount.as("viewCount"),
                        banner.insertTimestamp.as("insertTimestamp"),
                        banner.brand.brandId.as("brandId")
                ))
                .from(banner)
                .join(banner.bannerType, bannerTypeEntity)
                .leftJoin(banner.bannerImg, imageEntity);

        BooleanBuilder builder = new BooleanBuilder();
        
        // 기본 조건: 활성화된 배너만
        builder.and(banner.activeYn.eq('Y'));
        builder.and(banner.bannerType.activeYn.eq('Y'));

        // 배너 노출 기간 조건 추가
        LocalDate today = LocalDate.now();
        builder.and(banner.bannerStart.loe(today));
        builder.and(banner.bannerEnd.goe(today));

        // 배너 타입 필터링
        if (bannerTypeCd != null) {
            builder.and(banner.bannerType.bannerTypeCd.eq(bannerTypeCd));
        }

        // storeId가 있는 경우의 필터링
        if (storeId != null) {
            // 1. fullExpYn = 'Y'인 배너
            // 2. fullExpYn = 'N'이고 brandId가 store의 brandId와 일치하는 배너
            // 3. fullExpYn = 'N'이고 brandId가 null이며 stores에 storeId가 포함된 배너
            builder.and(
                banner.fullExpYn.eq('Y')
                .or(
                    banner.fullExpYn.eq('N')
                    .and(
                        banner.brand.brandId.eq(
                            queryFactory
                                .select(store.brand.brandId)
                                .from(store)
                                .where(store.storeId.eq(storeId))
                                .fetchOne()
                        )
                    )
                )
                .or(
                    banner.fullExpYn.eq('N')
                    .and(banner.brand.isNull())
                    .and(
                        banner.stores.any().storeId.eq(storeId)
                    )
                )
            );
        }

        query.where(builder);

        long total = query.fetchCount();
        List<BannerResponseDto> results = query
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(banner.bannerId.desc())
            .fetch();

        return new PageImpl<>(results, pageable, total);
    }
}