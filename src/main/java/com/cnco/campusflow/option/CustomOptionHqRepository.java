package com.cnco.campusflow.option;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomOptionHqRepository {
    Page<OptionHqWithGrpResponseDto> findOptions(Long brandId, Long optGrpId, String optionNm, Pageable pageable);
}
