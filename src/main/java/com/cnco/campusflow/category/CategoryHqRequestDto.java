package com.cnco.campusflow.category;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryHqRequestDto {
    private Long brandId;       // 연결된 브랜드 ID
    private String categoryHqNm; // 카테고리명
    private Integer orderNum;    // 순서 번호
}
