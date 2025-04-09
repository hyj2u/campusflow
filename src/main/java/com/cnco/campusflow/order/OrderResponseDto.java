package com.cnco.campusflow.order;


import com.cnco.campusflow.menu.MenuResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class OrderResponseDto {

    private Long orderId;
    private ConsumerResponseDto consumer;
    private List<MenuResponseDto> menus;
    private Integer totalPrice;



}

