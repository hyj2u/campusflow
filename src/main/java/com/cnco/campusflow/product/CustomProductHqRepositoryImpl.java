package com.cnco.campusflow.product;


import com.cnco.campusflow.brand.QBrandEntity;
import com.cnco.campusflow.category.CategoryHqResponseDto;
import com.cnco.campusflow.category.QCategoryHqEntity;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

public class CustomProductHqRepositoryImpl implements CustomProductHqRepository {
    private final JPAQueryFactory queryFactory;

    @Value("${image.base.url}")
    private String imageBaseUrl;

    private final QProductHqEntity product = QProductHqEntity.productHqEntity;
    private final QCategoryHqEntity category = QCategoryHqEntity.categoryHqEntity;




    @Override
    public Page<ProductHqResponseDto> findByCategoryAndProductNameAndActiveYn(Long categoryId, String productName, String activeYn, Pageable pageable) {

        JPQLQuery<ProductHqResponseDto> query = queryFactory.select(Projections.constructor(
                        ProductHqResponseDto.class,
                        product.productHqId,
                        product.productHqNm,
                        category.categoryHqId,
                        category.categoryHqNm,
                        product.price,
                        product.insertTimestamp,
                        Expressions.stringTemplate(
                                "CONCAT({0}, '/', {1})", imageBaseUrl, product.mainImg.imageId
                        ).as("mainImageUrl"),
                        product.activeYn
                ))
                .from(product)
                .join(product.categories, category) // ManyToMany 관계의 컬렉션 조인
                .where(
                        categoryIdEq(category, categoryId),
                        productNameContains(product, productName),
                        activeYnEq(product, activeYn)
                );

        long total = query.fetchCount();
        List<ProductHqResponseDto> results = query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<ProductHqResponseDto> findProductsNotInCategory(Long categoryId, String productName, String activeYn, Pageable pageable) {
        JPQLQuery<ProductHqResponseDto> query = queryFactory.select(Projections.constructor(
                        ProductHqResponseDto.class,
                        product.productHqId,
                        product.productHqNm,
                        category.categoryHqId,
                        category.categoryHqNm,
                        product.price,
                        product.insertTimestamp,
                        Expressions.stringTemplate(
                                "CONCAT({0}, '/', {1})", imageBaseUrl, product.mainImg.imageId
                        ).as("mainImageUrl"),
                        product.activeYn
                ))
                .from(product)
                .join(product.categories, category) // ManyToMany 관계의 컬렉션 조인
                .where(
                        categoryNotLinkedWithProduct(categoryId),
                        productNameContains(product, productName),
                        activeYnEq(product, activeYn)
                );

        long total = query.fetchCount();
        List<ProductHqResponseDto> results = query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public Page<ProductHqListResponseDto> findProducts(
            String activeYn, Long brandId, Long categoryId, String search, Pageable pageable) {

        JPQLQuery<Tuple> query = queryFactory.select(
                        product.productHqId,
                        product.productHqNm,
                        product.productHqEngNm,
                        product.price,
                        product.insertTimestamp,
                        product.activeYn,
                        Expressions.stringTemplate(
                                "CONCAT({0}, '/', {1})", imageBaseUrl, product.mainImg.imageId
                        ).as("mainImageUrl"),
                        category.categoryHqId,
                        category.categoryHqNm,
                        category.orderNum,
                        category.brand.brandNm,
                        category.brand.brandId
                )
                .from(product)
                .leftJoin(product.categories, category) // Product와 Category를 조인
                .where(
                        activeYnEq(product, activeYn),
                        categoryIdEq(category, categoryId),
                        brandIdEq(category.brand, brandId),
                        searchContains(product, search)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<Tuple> results = query.fetch();

        // Group by productId and merge categories
        Map<Long, ProductHqListResponseDto> productMap = new LinkedHashMap<>();
        for (Tuple tuple : results) {
            Long productId = tuple.get(product.productHqId);
            ProductHqListResponseDto productDto = productMap.getOrDefault(productId, new ProductHqListResponseDto(
                    productId,
                    tuple.get(product.productHqNm),
                    tuple.get(product.productHqEngNm),
                    new ArrayList<>(), // Categories
                    tuple.get(product.price),
                    tuple.get(product.insertTimestamp),
                    tuple.get(Expressions.stringTemplate("CONCAT({0}, '/', {1})", imageBaseUrl, product.mainImg.imageId)),
                    tuple.get(product.activeYn)
            ));

            // Add category if it exists
            if (tuple.get(category.categoryHqId) != null) {
                CategoryHqResponseDto categoryDto = new CategoryHqResponseDto(
                        tuple.get(category.categoryHqId),
                        tuple.get(category.categoryHqNm),
                        tuple.get(category.orderNum),
                        tuple.get(category.brand.brandNm),
                        tuple.get(category.brand.brandId)
                );
                productDto.getCategories().add(categoryDto);
            }

            productMap.put(productId, productDto);
        }

        List<ProductHqListResponseDto> finalResults = new ArrayList<>(productMap.values());
        long total = query.fetchCount();

        return new PageImpl<>(finalResults, pageable, total);
    }

    private BooleanExpression brandIdEq(QBrandEntity brand, Long brandId) {
        return brandId != null ? brand.brandId.eq(brandId) : null;
    }

    private BooleanExpression searchContains(QProductHqEntity product, String search) {
        return search != null ? product.productHqNm.containsIgnoreCase(search)
                .or(product.productHqEngNm.containsIgnoreCase(search)) : null;
    }

    private BooleanExpression categoryNotLinkedWithProduct(Long categoryId) {

        if (categoryId == null) {
            return null; // 조건을 무시
        }

        // 서브쿼리: 특정 카테고리와 연결된 상품 ID를 가져옵니다.
        JPQLQuery<Long> subQuery = queryFactory.select(product.productHqId)
                .from(product)
                .join(product.categories, category)
                .where(category.categoryHqId.eq(categoryId));

        // 메인 쿼리에서 해당 상품 제외
        return product.productHqId.notIn(subQuery);
    }

    private BooleanExpression categoryIdEq(QCategoryHqEntity category, Long categoryId) {
        return categoryId != null ? category.categoryHqId.eq(categoryId) : null;
    }

    private BooleanExpression productNameContains(QProductHqEntity product, String productName) {
        return productName != null ? product.productHqNm.containsIgnoreCase(productName) : null;
    }

    private BooleanExpression activeYnEq(QProductHqEntity product, String activeYn) {
        return activeYn != null ? product.activeYn.eq(activeYn) : null;
    }
}
