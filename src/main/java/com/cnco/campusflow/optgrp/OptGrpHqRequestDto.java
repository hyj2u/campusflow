package com.cnco.campusflow.optgrp;

import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptGrpHqRequestDto {
    private Long optGrpId;
    private String optGrpNm;   // 옵션 그룹 이름
    private Long brandId;       // 브랜드 ID
    private String orderNum;    // 정렬 순서
}
