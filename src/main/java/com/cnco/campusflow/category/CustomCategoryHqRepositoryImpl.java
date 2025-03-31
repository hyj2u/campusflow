package com.cnco.campusflow.category;


import com.cnco.campusflow.brand.QBrandEntity;
import com.querydsl.core.types.Projections;
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
public class CustomCategoryHqRepositoryImpl implements CustomCategoryHqRepository {
    private final JPAQueryFactory queryFactory;
     @Override
    public Page<CategoryHqResponseDto> findCategories(Long brandId, String categoryHqNm, Pageable pageable) {
        QCategoryHqEntity category = QCategoryHqEntity.categoryHqEntity;
        QBrandEntity brand = QBrandEntity.brandEntity;

        JPQLQuery<CategoryHqResponseDto> query = queryFactory
                .select(Projections.fields(
                        CategoryHqResponseDto.class,
                        category.categoryHqId.as("categoryHqId"),
                        category.categoryHqNm.as("categoryHqNm"),
                        category.orderNum.as("orderNum"),
                        brand.brandNm.as("brandNm"),
                        brand.brandId.as("brandId")
                ))
                .from(category)
                .join(category.brand, brand);

        if (brandId != null) {
            query.where(brand.brandId.eq(brandId));
        }

        if (categoryHqNm != null && !categoryHqNm.isEmpty()) {
            query.where(category.categoryHqNm.containsIgnoreCase(categoryHqNm));
        }

        long total = query.fetchCount(); // fetchCount를 사용하여 총 개수 조회
        List<CategoryHqResponseDto> results = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(category.orderNum.asc())
                .fetch();

        return new PageImpl<>(results, pageable, total);
    }
}
