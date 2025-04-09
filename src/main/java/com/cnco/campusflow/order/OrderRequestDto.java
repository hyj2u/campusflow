package com.cnco.campusflow.order;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class OrderRequestDto {


    private Long consumerId;
    private List<Long> menuIds;

    private Integer totalPrice;



}

