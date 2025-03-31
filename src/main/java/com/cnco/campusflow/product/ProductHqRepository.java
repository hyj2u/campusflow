package com.cnco.campusflow.product;

import com.cnco.campusflow.category.CategoryHqEntity;
import com.cnco.campusflow.option.OptionHqEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductHqRepository extends JpaRepository<ProductHqEntity, Long>  , CustomProductHqRepository{
    List<ProductHqEntity> findAllByOptionsContaining(OptionHqEntity option);
    List<ProductHqEntity> findAllByCategories(CategoryHqEntity categoryHqEntity);
}
