package com.cnco.campusflow.option;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OptionHqResponseDto {
    private Long optionHqId;
    private String optionHqNm;
    private String codeNm; // 코드 이름
    private String requireYn;


}
