package com.cnco.campusflow.optdtl;

import lombok.Data;

@Data
public class OptDtlHqRequestDto {
    private Long optDtlHqId;
    private String opDtlHqNm;
    private Integer min;
    private Integer max;
    private Integer price;
    private String type;
    private Integer unitPrice;
}
