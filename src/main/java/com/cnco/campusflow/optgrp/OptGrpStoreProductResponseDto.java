package com.cnco.campusflow.optgrp;

import com.cnco.campusflow.option.OptionWtDtlStoreResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class OptGrpStoreProductResponseDto {
    private Long optGrpId;
    private String optGrpNm;
    private List<OptionWtDtlStoreResponseDto> options;
}
