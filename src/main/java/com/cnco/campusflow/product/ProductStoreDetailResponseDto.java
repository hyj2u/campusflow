package com.cnco.campusflow.product;

import com.cnco.campusflow.optgrp.OptGrpStoreProductResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductStoreDetailResponseDto {
    private Long productId;
    private String productNm;
    private Integer price;

    private String stampUseYn;
    private Integer stampUseCnt;

    private List<ProductTagResponseDto> tags; // 태그 목록
    private List<OptGrpStoreProductResponseDto> optGrps;
}
