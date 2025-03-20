package com.cnco.campusflow.brand;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class BrandMappingImpl implements BrandMapping{
    private Long brandId;
    private String brandNm;
}
