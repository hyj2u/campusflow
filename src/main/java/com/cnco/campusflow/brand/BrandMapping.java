package com.cnco.campusflow.brand;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface BrandMapping  {
    Long getBrandId();
    String getBrandNm();
}
