package com.cnco.campusflow.product;

import lombok.AllArgsConstructor;
import lombok.Data;


//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
public class ProductTagResponseDto {

    private Long productTagId;

    private String productTagNm;
    private String showYn;


}
