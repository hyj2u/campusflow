package com.cnco.campusflow.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class MenuOptionDto  {

    private Long menuOptId;
    private Long optionId;

    private String optionNm;
    private String codeNm;
    private Integer chosenNum;
    private Integer totalPrice;
    private Long optDtlId;
    private String optDtlNm;
    private Integer min;

    private Integer max;

    private Integer price;

    private String type;

    private Integer unitPrice;

}

