package com.cnco.campusflow.option;

import com.cnco.campusflow.optgrp.OptGrpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<OptionEntity, Long> {

    List<OptionEntity> findByOptGrpIn(List<OptGrpEntity> optGrps);
}
