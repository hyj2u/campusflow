package com.cnco.campusflow.product;


import com.cnco.campusflow.category.CategoryBasicResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductListResponseDto {
    private Long productId;
    private String productNm;

    private List<CategoryBasicResponseDto> categories; // List of categories

    private List<ProductTagResponseDto> tags;
    private String mainImageUrl;
    private String activeYn;
}
