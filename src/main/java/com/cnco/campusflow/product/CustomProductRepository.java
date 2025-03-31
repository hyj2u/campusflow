package com.cnco.campusflow.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomProductRepository {
    Page<ProductListResponseDto> findByStoreAndCategoryAndProductNameAndActiveYn(Long storeId, Long categoryId, String productName, String activeYn, Pageable pageable);
    Page<ProductStoreResponseDto> getProductStores(Long productHqId, String search, Pageable pageable);

}
