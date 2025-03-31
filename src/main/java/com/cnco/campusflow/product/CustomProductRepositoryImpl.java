package com.cnco.campusflow.product;

import com.cnco.campusflow.brand.QBrandEntity;
import com.cnco.campusflow.category.CategoryBasicResponseDto;
import com.cnco.campusflow.category.QCategoryEntity;
import com.cnco.campusflow.store.QStoreEntity;
import com.querydsl.core.Tuple;
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

public class CustomProductRepositoryImpl implements CustomProductRepository {
    private final JPAQueryFactory queryFactory;

    @Value("${image.base.url}")
    private String imageBaseUrl;

    private final QProductEntity product = QProductEntity.productEntity;
    private final QProductTagEntity productTag = QProductTagEntity.productTagEntity;
    private final QStoreEntity store = QStoreEntity.storeEntity;
    private final QBrandEntity brand = QBrandEntity.brandEntity;
    private final QCategoryEntity category = QCategoryEntity.categoryEntity;
    public Page<ProductStoreResponseDto> getProductStores(
            Long productHqId, String search, Pageable pageable) {


        JPQLQuery<Tuple> query = queryFactory.selectDistinct(
                        brand.brandNm,
                        store.storeNm,
                        store.storeId,
                        store.owner,
                        store.managerPhone,
                        product.price,
                        product.activeYn
                )
                .from(product)
                .join(product.store, store)
                .join(store.brand, brand)
                .where(
                        productHqIdEq(product, productHqId),
                        searchContains(store, search)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<Tuple> results = query.fetch();

        List<ProductStoreResponseDto> responseDtos = results.stream()
                .map(tuple -> new ProductStoreResponseDto(
                        tuple.get(brand.brandNm),
                        tuple.get(store.storeNm),
                        tuple.get(store.storeId),
                        tuple.get(store.owner),
                        tuple.get(store.managerPhone),
                        tuple.get(product.price),
                        tuple.get(product.activeYn)
                ))
                .toList();

        long total = query.fetchCount();

        return new PageImpl<>(responseDtos, pageable, total);
    }

    @Override
    public Page<ProductListResponseDto> findByStoreAndCategoryAndProductNameAndActiveYn(Long storeId, Long categoryId, String productName, String activeYn, Pageable pageable) {
        if (storeId == null) {
            throw new IllegalArgumentException("storeId cannot be null.");
        }

        JPQLQuery<Tuple> query = queryFactory.select(
                        product.productId,
                        product.productNm,
                        category.categoryId,
                        category.categoryNm,
                        product.mainImg.imageId,
                        product.activeYn,
                        productTag.productTagId, // 태그 ID
                        productTag.productTagNm, // 태그 이름
                        productTag.showYn
                )
                .from(product)
                .leftJoin(product.categories, category) // 카테고리 조인
                .leftJoin(product.productTags, productTag) // 태그 조인
                .where(
                        product.store.storeId.eq(storeId), // storeId는 필수 조건
                        categoryIdEq(category, categoryId),
                        productNameContains(product, productName),
                        activeYnEq(product, activeYn)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<Tuple> results = query.fetch();

        Map<Long, ProductListResponseDto> productMap = new LinkedHashMap<>();
        for (Tuple tuple : results) {
            Long productId = tuple.get(product.productId);

            ProductListResponseDto productDto = productMap.getOrDefault(productId, new ProductListResponseDto(
                    productId,
                    tuple.get(product.productNm),
                    new ArrayList<>(), // Categories 초기화
                    new ArrayList<>(), // Tags 초기화
                    tuple.get(Expressions.stringTemplate("CONCAT({0}, '/', {1})", imageBaseUrl, tuple.get(product.mainImg.imageId))),
                    tuple.get(product.activeYn)
            ));

            if (tuple.get(category.categoryId) != null) {
                CategoryBasicResponseDto categoryDto = new CategoryBasicResponseDto(
                        tuple.get(category.categoryId),
                        tuple.get(category.categoryNm)
                );
                productDto.getCategories().add(categoryDto);
            }
            // 태그 추가
            if (tuple.get(productTag.productTagId) != null) {
                ProductTagResponseDto tagDto = new ProductTagResponseDto(
                        tuple.get(productTag.productTagId),
                        tuple.get(productTag.productTagNm),
                        tuple.get(productTag.showYn)
                );
                productDto.getTags().add(tagDto);
            }

            productMap.put(productId, productDto);
        }

        return new PageImpl<>(new ArrayList<>(productMap.values()), pageable, query.fetchCount());
    }

    private BooleanExpression categoryIdEq(QCategoryEntity category, Long categoryId) {
        return categoryId != null ? category.categoryId.eq(categoryId) : null;
    }

    private BooleanExpression productNameContains(QProductEntity product, String productName) {
        return productName != null ? product.productNm.containsIgnoreCase(productName) : null;
    }

    private BooleanExpression activeYnEq(QProductEntity product, String activeYn) {
        return activeYn != null ? product.activeYn.eq(activeYn) : null;
    }

    private BooleanExpression productHqIdEq(QProductEntity product, Long productHqId) {
        return productHqId != null ? product.productHqEntity.productHqId.eq(productHqId) : null;
    }
    private BooleanExpression searchContains(QStoreEntity store, String search) {
        if (search == null || search.trim().isEmpty()) {
            return null;
        }
        return store.owner.containsIgnoreCase(search)
                .or(store.storeNm.containsIgnoreCase(search))
                .or(store.managerPhone.containsIgnoreCase(search));
    }
}
