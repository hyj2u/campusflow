package com.cnco.campusflow.optdtl;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class OptDtlRequestDto {

    private Long optDtlId;

    private Integer min;

    private Integer max;

    private Integer price;

    private String type;

    private Integer unitPrice;

    private String dtlUseYn;

}

