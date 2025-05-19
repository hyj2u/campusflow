package com.cnco.campusflow.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "OrderMenuDto",
    description = """
        주문 메뉴 DTO
        
        * 주문하기위한 메뉴와 수량 정보
        """,
    example = """
        {
            "menuId": 1,
            "orderCnt" : 2
        }
        """
)
public class OrderMenuDto {


    @Schema(description = "주문 메뉴 ID", example = "1")
    private Long menuId;

    @Schema(description = "주문 수량", example = "2")
    private Integer orderCnt;
}

