package com.cnco.campusflow.product;


import com.cnco.campusflow.category.CategoryHqResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductHqListResponseDto {
    private Long productId;
    private String productNm;
    private String productEngNm;
    private List<CategoryHqResponseDto> categories; // List of categories
    private Integer price;
    private LocalDateTime insertTimestamp;
    private String mainImageUrl;
    private String activeYn;
}
