package com.cnco.campusflow.order;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ConsumerAddressDto {

    private Long orderAddrId;
    private String addressMain;
    private String addressDtl;
    private String defaultYn;

}

