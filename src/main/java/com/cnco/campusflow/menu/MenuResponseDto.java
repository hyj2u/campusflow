package com.cnco.campusflow.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class MenuResponseDto {

    private Long menuId;

    private Long storeId;
    private String storeName;
    private Long productId;
    private String productName;

    private List<MenuOptionDto> options; // 세부 옵션 리스트

}

