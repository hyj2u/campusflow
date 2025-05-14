package com.cnco.campusflow.product;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductHqResponseDto {
    private Long productId;
    private String productNm;
    private Long categoryId;
    private String categoryNm;
    private Integer price;
    private LocalDateTime insertTimestamp;
    private String mainImageUrl;
    private String activeYn;
}
