package com.cnco.campusflow.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CustomCategoryHqRepository {
    Page<CategoryHqResponseDto> findCategories(Long brandId, String categoryHqNm, Pageable pageable);
}
