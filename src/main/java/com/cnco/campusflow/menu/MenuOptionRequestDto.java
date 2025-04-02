package com.cnco.campusflow.menu;

import com.cnco.campusflow.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class MenuOptionRequestDto extends BaseEntity {

    private Long optionId;
    private Long optDtlId;
    private Integer chosenNum;
    private Integer totalPrice;

}

