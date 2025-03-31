package com.cnco.campusflow.optgrp;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomOptGrpHqRepository {
    Page<OptGrpListHqResponseDto> findOptionGroupsWithDetails(Long brandId, String search, Pageable pageable);
}
