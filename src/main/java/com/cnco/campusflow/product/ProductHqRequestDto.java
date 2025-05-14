package com.cnco.campusflow.product;

import lombok.Data;

import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductHqRequestDto {
    private Long productHqId;
    private String productHqNm;
    private List<Long> categoryIds; // 카테고리 ID 목록
    private String productHqEngNm;
    private Integer price;
    private Integer buyCnt;
    private String activeYn;
    private String stampUseYn;
    private String appVisibleYn;
    private String kioskVisibleYn;
    private String productDtl;
    private String originInfo;
    private String allergyInfo;
    private List<ProductTagHqEntity> productTags;

}
