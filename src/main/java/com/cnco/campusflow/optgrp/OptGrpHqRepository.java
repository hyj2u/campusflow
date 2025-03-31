package com.cnco.campusflow.optgrp;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptGrpHqRepository extends JpaRepository<OptGrpHqEntity, Long>, CustomOptGrpHqRepository {
    List<OptGrpHqEntity> findByBrandBrandId(Long brandId);
}
