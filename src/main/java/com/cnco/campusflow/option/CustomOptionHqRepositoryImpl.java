package com.cnco.campusflow.option;


import com.cnco.campusflow.optgrp.QOptGrpHqEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository

public class CustomOptionHqRepositoryImpl implements CustomOptionHqRepository {
    private final JPAQueryFactory queryFactory;
    private final QOptionHqEntity option = QOptionHqEntity.optionHqEntity;
    private final QOptGrpHqEntity optGrp = QOptGrpHqEntity.optGrpHqEntity;


    @Override
    public Page<OptionHqWithGrpResponseDto> findOptions(Long brandId, Long optGrpId, String optionNm, Pageable pageable) {
        JPQLQuery<OptionHqWithGrpResponseDto> query = queryFactory.select(Projections.constructor(
                        OptionHqWithGrpResponseDto.class,
                        option.optionHqId,
                        option.optionHqNm,
                        optGrp.optGrpHqId,
                        optGrp.optGrpHqNm,
                        option.optGrp.brand.brandNm
                ))
                .from(option)
                .join(option.optGrp, optGrp) // 옵션과 옵션 그룹 조인
                .where(
                        brandIdEq(option, brandId),
                        optGrpIdEq(optGrp, optGrpId),
                        optionNmContains(option, optionNm)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        long total = query.fetchCount();
        List<OptionHqWithGrpResponseDto> results = query.fetch();

        return new PageImpl<>(results, pageable, total);
    }

    private BooleanExpression brandIdEq(QOptionHqEntity option, Long brandId) {
        return brandId != null ? option.optGrp.brand.brandId.eq(brandId) : null;
    }

    private BooleanExpression optGrpIdEq(QOptGrpHqEntity optGrp, Long optGrpId) {
        return optGrpId != null ? optGrp.optGrpHqId.eq(optGrpId) : null;
    }

    private BooleanExpression optionNmContains(QOptionHqEntity option, String optionNm) {
        return optionNm != null ? option.optionHqNm.containsIgnoreCase(optionNm) : null;
    }
}
