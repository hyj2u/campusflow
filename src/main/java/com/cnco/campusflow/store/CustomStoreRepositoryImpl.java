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
    public Page<StoreListDto> findStores(String search,  double latitude, double longitude, Pageable pageable) {
        BooleanBuilder whereClause = new BooleanBuilder();
        whereClause.and(storeEntity.storeStatus.eq("O"));

        if (search != null && !search.trim().isEmpty()) {
            BooleanBuilder searchCondition = new BooleanBuilder();
            searchCondition.or(storeEntity.storeNm.containsIgnoreCase(search));
            searchCondition.or(storeEntity.addressMain.containsIgnoreCase(search));
            searchCondition.or(storeEntity.addressDtl.containsIgnoreCase(search));
            whereClause.and(searchCondition);
        }

        // Haversine distance formula
        NumberExpression<Double> distanceExpr = Expressions.numberTemplate(Double.class,
                "6371 * acos(cos(radians({0})) * cos(radians({1})) * cos(radians({2}) - radians({3})) + sin(radians({0})) * sin(radians({1})))",
                latitude, storeEntity.latitude, longitude, storeEntity.longitude
        );

        // QueryDSL에서 StoreListDto로 직접 매핑
        List<StoreListDto> results = queryFactory
                .select(Projections.constructor(StoreListDto.class,
                        storeEntity.storeId,
                        storeEntity.storeNm,
                        storeEntity.owner,
                        storeEntity.addressMain,
                        storeEntity.addressDtl,
                        distanceExpr.as("distance"),
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
                .orderBy(distanceExpr.asc()) // 거리 가까운 순 정렬
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // total count for pagination
        long total = queryFactory
                .select(storeEntity.count())
                .from(storeEntity)
                .where(whereClause)
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }


}
