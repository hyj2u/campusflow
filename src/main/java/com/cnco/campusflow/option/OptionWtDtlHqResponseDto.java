package com.cnco.campusflow.option;

import com.cnco.campusflow.optdtl.OptDtlHqEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OptionWtDtlHqResponseDto {
    private Long optionHqId;
    private String optionHqNm;
    private Long optGrpHqId;
    private String optGrpHqNm;
    private String requireYn;
    private List<OptDtlHqEntity> optionDetails;
}
