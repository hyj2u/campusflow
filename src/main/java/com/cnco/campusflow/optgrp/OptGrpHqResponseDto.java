package com.cnco.campusflow.optgrp;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptGrpHqResponseDto {
    private Long optGrpHqId;
    private String optGrpHqNm;
    private String orderNum;
    private Long brandId;
    private String brandNm;
}
