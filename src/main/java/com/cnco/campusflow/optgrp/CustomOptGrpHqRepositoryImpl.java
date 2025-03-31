package com.cnco.campusflow.optgrp;

import com.cnco.campusflow.brand.QBrandEntity;
import com.cnco.campusflow.code.QCodeEntity;
import com.cnco.campusflow.option.OptionHqResponseDto;
import com.cnco.campusflow.option.QOptionHqEntity;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository

public class CustomOptGrpHqRepositoryImpl implements CustomOptGrpHqRepository {

    private final JPAQueryFactory queryFactory;

    private final QOptionHqEntity option = QOptionHqEntity.optionHqEntity;
    private final QOptGrpHqEntity optGrp = QOptGrpHqEntity.optGrpHqEntity;
    private final QCodeEntity code = QCodeEntity.codeEntity;
    private final QBrandEntity brand = QBrandEntity.brandEntity;


    @Override
    public Page<OptGrpListHqResponseDto> findOptionGroupsWithDetails(
            Long brandId, String search, Pageable pageable) {
        JPQLQuery<Tuple> query = queryFactory.select(
                        optGrp.optGrpHqId,
                        optGrp.optGrpHqNm,
                        optGrp.brand.brandId,
                        optGrp.brand.brandNm,
                        Expressions.numberTemplate(Integer.class, "CAST({0} AS INTEGER)", optGrp.orderNum),
                        option.optionHqId,
                        option.optionHqNm,
                        option.code.codeNm,
                        option.requireYn
                )
                .from(optGrp)
                .leftJoin(option).on(option.optGrp.eq(optGrp)) // LEFT JOIN
                .leftJoin(option.code, code) // LEFT JOIN
                .leftJoin(optGrp.brand, brand) // LEFT JOIN
                .where(
                        brandIdEq(optGrp, brandId),
                        searchContains(optGrp, option, search)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<Tuple> results = query.fetch();

        // 데이터 그룹화
        Map<Long, OptGrpListHqResponseDto> groupedResults = new LinkedHashMap<>();
        for (Tuple tuple : results) {
            Long optGrpHqId = tuple.get(optGrp.optGrpHqId);

            OptGrpListHqResponseDto dto = groupedResults.computeIfAbsent(optGrpHqId, id -> new OptGrpListHqResponseDto(
                    id,
                    tuple.get(optGrp.optGrpHqNm),
                    tuple.get(optGrp.brand.brandId),
                    tuple.get(optGrp.brand.brandNm),
                    tuple.get(Expressions.numberTemplate(Integer.class, "CAST({0} AS INTEGER)", optGrp.orderNum)),
                    new ArrayList<>()
            ));

            // 옵션 추가
            if (tuple.get(option.optionHqId) != null) {
                OptionHqResponseDto optionDto = new OptionHqResponseDto(
                        tuple.get(option.optionHqId),
                        tuple.get(option.optionHqNm),
                        tuple.get(option.code.codeNm),
                        tuple.get(option.requireYn)
                );
                dto.getOptions().add(optionDto);
            }
        }

        return new PageImpl<>(new ArrayList<>(groupedResults.values()), pageable, query.fetchCount());
    }

    private BooleanExpression searchContains(QOptGrpHqEntity optGrp, QOptionHqEntity option, String search) {
        if (search == null || search.trim().isEmpty()) {
            return null;
        }
        return optGrp.optGrpHqNm.containsIgnoreCase(search)
                .or(option.optionHqNm.containsIgnoreCase(search));
    }

    private BooleanExpression brandIdEq(QOptGrpHqEntity optGrp, Long brandId) {
        return brandId != null ? optGrp.brand.brandId.eq(brandId) : null;
    }
}
