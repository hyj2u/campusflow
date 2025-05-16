package com.cnco.campusflow.optgrp;


import com.cnco.campusflow.option.OptionHqResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptGrpListHqResponseDto {
    private Long optGrpHqId;
    private String optGrpHqNm;
    private Long brandId;
    private String brandNm;
    private Integer orderNum;
    private List<OptionHqResponseDto> options;
}
