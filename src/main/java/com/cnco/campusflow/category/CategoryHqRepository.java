package com.cnco.campusflow.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryHqRepository extends JpaRepository<CategoryHqEntity, Long> , CustomCategoryHqRepository {
    List<CategoryHqEntity> findAllByBrandBrandId(Long brandId);

}
