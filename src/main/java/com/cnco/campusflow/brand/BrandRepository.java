package com.cnco.campusflow.brand;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<BrandEntity, Long> {

    List<BrandEntity> findAllByOrderByOrderNum();
    boolean existsByBrandNm(String brandNm);


}
