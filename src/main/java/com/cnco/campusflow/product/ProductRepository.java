package com.cnco.campusflow.product;


import com.cnco.campusflow.category.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>, CustomProductRepository  {

    void deleteByStoreStoreIdAndCategoriesContaining(Long storeId, CategoryEntity category);
    List<ProductEntity> findByCategoriesCategoryIdOrderByInsertTimestampDesc(Long categoryId);
}
