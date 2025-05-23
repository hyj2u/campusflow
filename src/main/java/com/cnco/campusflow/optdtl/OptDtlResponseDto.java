package com.cnco.campusflow.optdtl;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptDtlResponseDto {

    private Long optDtlId;


    private String opDtlNm;

    private Integer min;

    private Integer max;

    private Integer price;

    private String type;

    private Integer unitPrice;
    private String dtlUseYn;



}

