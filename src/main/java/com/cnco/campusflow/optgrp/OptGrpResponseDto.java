package com.cnco.campusflow.optgrp;

import com.cnco.campusflow.option.OptionResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptGrpResponseDto {
    private Long optGrpId;
    private String optGrpNm;
    private String orderNum;
    private List<OptionResponseDto> options;
}
