package com.cnco.campusflow.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductStoreDetailRequestDto {
    private Long productId;
    private String productNm;
    private Integer price;

    private String stampUseYn;
    private Integer stampUseCnt;
    private List<ProductTagEntity> tags; // 태그 목록

}
