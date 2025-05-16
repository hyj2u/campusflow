package com.cnco.campusflow.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDto {
    private Long categoryId;
    private String categoryNm;
    private Integer orderNum;
    private String brandNm; // 브랜드 이름만 포함
    private Long brandId;


}
