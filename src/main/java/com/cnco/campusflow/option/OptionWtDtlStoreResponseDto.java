package com.cnco.campusflow.option;

import com.cnco.campusflow.optdtl.OptDtlResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OptionWtDtlStoreResponseDto {
    private Long optionId;
    private String optionNm;

    private List<OptDtlResponseDto> optionDetails;
}
