package com.cnco.campusflow.option;

import com.cnco.campusflow.optdtl.OptDtlHqEntity;
import lombok.Data;

import java.util.List;

@Data
public class OptionHqRequestDto {
    private Long optGrpHqId;
    private Long optionHqId;
    private String optionHqNm;
    private Long codeId;
    private String requireYn;
    private List<OptDtlHqEntity> optionDtls;
}
