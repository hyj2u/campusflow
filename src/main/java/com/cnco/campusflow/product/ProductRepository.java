package com.cnco.campusflow.product;


import com.cnco.campusflow.category.CategoryEntity;
import com.cnco.campusflow.store.StoreService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>, CustomProductRepository  {


    List<ProductEntity> findByCategoriesCategoryIdOrderByInsertTimestampDesc(Long categoryId);
    List<ProductEntity> findAllByStoreStoreIdAndActiveYnOrderByInsertTimestampDesc(Long storeId, String activeYn);
}
