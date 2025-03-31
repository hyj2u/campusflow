package com.cnco.campusflow.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomProductHqRepository {
    Page<ProductHqResponseDto> findByCategoryAndProductNameAndActiveYn(Long categoryId, String productName, String activeYn, Pageable pageable);
    Page<ProductHqResponseDto> findProductsNotInCategory(Long categoryId, String productName, String activeYn, Pageable pageable);
    Page<ProductHqListResponseDto> findProducts(String activeYn, Long brandId, Long categoryId, String search,  Pageable pageable);
}
