package com.cnco.campusflow.store;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class CustomStoreRepositoryImpl implements CustomStoreRepository {
    private final JPAQueryFactory queryFactory;
    private QStoreEntity storeEntity = QStoreEntity.storeEntity;

    @Override
    public Page<StoreEntity> findStores(String search, Pageable pageable) {
        BooleanBuilder whereClause = new BooleanBuilder();
        whereClause.and(storeEntity.openYn.eq("Y"));
        // search 필터링 (storeNm, adressMain, adressDtl 중 하나라도 LIKE 검색)
        if (search != null && !search.trim().isEmpty()) {
            BooleanBuilder searchCondition = new BooleanBuilder();
            searchCondition.or(storeEntity.storeNm.containsIgnoreCase(search));
            searchCondition.or(storeEntity.addressMain.containsIgnoreCase(search));
            searchCondition.or(storeEntity.addressDtl.containsIgnoreCase(search));
            whereClause.and(searchCondition);
        }


        // QueryDSL 쿼리 실행
        List<StoreEntity> results = queryFactory
                .selectFrom(storeEntity)
                .where(whereClause)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 개수 조회
        long total = queryFactory
                .select(storeEntity.count())
                .from(storeEntity)
                .where(whereClause)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<StoreListDto> findStoresNear(double latitude, double longitude, Pageable pageable) {
        BooleanBuilder whereClause = new BooleanBuilder();

        // openYn = 'Y' 조건 추가
        whereClause.and(storeEntity.openYn.eq("Y"));


        // Haversine 공식 적용 (현재 위치 vs 매장 위치 거리 계산)
        NumberExpression<Double> distanceExpression = Expressions.numberTemplate(Double.class,
                "6371 * acos(cos(radians({0})) * cos(radians({1})) * cos(radians({2}) - radians({3})) + sin(radians({0})) * sin(radians({1})))",
                latitude, storeEntity.latitude, longitude, storeEntity.longitude);



        // QueryDSL에서 `StoreListDto` 직접 반환
        List<StoreListDto> results = queryFactory
                .select(Projections.constructor(StoreListDto.class,
                        storeEntity.storeId,
                        storeEntity.storeNm,
                        storeEntity.owner,
                        storeEntity.addressMain,
                        storeEntity.addressDtl,
                        distanceExpression.as("distance"), // 거리 필드 추가
                        storeEntity.dayOpenTm,
                        storeEntity.dayCloseTm,
                        storeEntity.satOpenTm,
                        storeEntity.satCloseTm,
                        storeEntity.sunOpenTm,
                        storeEntity.sunCloseTm,
                        storeEntity.deliveryYn,
                        storeEntity.togoYn,
                        storeEntity.inhereYn
                ))
                .from(storeEntity)
                .where(whereClause)
                .orderBy(distanceExpression.asc()) // 가까운 매장 순 정렬
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 개수 조회
        long total = queryFactory
                .select(storeEntity.count())
                .from(storeEntity)
                .where(whereClause)
                .fetchOne();
        return new PageImpl<>(results, pageable, total);
    }
}
