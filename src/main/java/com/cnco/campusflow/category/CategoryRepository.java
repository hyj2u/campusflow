package com.cnco.campusflow.category;


import com.cnco.campusflow.store.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findAllByStoreStoreId(Long storeId);

    boolean existsByStoreAndCategoryHqEntity(StoreEntity store, CategoryHqEntity categoryHqEntity);

}
