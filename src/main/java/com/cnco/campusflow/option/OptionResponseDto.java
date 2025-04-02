package com.cnco.campusflow.option;

import com.cnco.campusflow.optdtl.OptDtlResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptionResponseDto {
    private Long optionId;
    private String optionNm;
    private String codeNm; // 코드 이름
    private List<OptDtlResponseDto> details;

}
