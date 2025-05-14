package com.cnco.campusflow.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductStoreResponseDto {

    private String brandNm;
    private String storeNm;
    private Long storeId;
    private String owner;
    private String managerPhone;
    private Integer price;
    private String activeYn;

}
