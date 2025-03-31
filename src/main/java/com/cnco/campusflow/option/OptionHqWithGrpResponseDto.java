package com.cnco.campusflow.option;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptionHqWithGrpResponseDto {
    private Long optionHqId;
    private String optionHqNm;
    private Long optGrpHqId;
    private String optGrpHqNm;
    private String brandNm;

}
