package com.cnco.campusflow.product;


import com.cnco.campusflow.category.CategoryHqResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDetailResponseDto {
    private Long productId;
    private String productNm;
    private String productEngNm;
    private Integer price;
    private Integer buyCnt;
    private String activeYn;
    private String stampUseYn;
    private String appVisibleYn;
    private String kioskVisibleYn;
    private String productDtl;
    private String originInfo;
    private String allergyInfo;
    private List<CategoryHqResponseDto> categories; // 카테고리 목록
    private List<String> tags; // 태그 목록
    private String mainImageUrl; // 메인 이미지 URL
    private String originImageUrl; // 원산지 이미지 URL
    private String allergyImageUrl; // 알레르기 이미지 URL
}
