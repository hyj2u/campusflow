package com.cnco.campusflow.order;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ConsumerRequestDto {
    private Long consumerId;
    private Long orderAddrId;
    private String storeDemand;
    private String deliveryDemand;
}

