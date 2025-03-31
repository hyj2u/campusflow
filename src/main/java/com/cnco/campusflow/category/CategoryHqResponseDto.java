package com.cnco.campusflow.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class CategoryHqResponseDto {
    private Long categoryHqId;
    private String categoryHqNm;
    private Integer orderNum;
    private String brandNm; // 브랜드 이름만 포함
    private Long brandId;
}
