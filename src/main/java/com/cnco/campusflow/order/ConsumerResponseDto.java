package com.cnco.campusflow.order;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ConsumerResponseDto {

    private Long consumerId;
    private Long orderAddrId;
    private String addressMain;
    private String addressDtl;
    private String defaultYn;
    private String storeDemand;
    private String deliveryDemand;
    private String phone;

}

