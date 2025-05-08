package com.cnco.campusflow.order;

import com.cnco.campusflow.menu.MenuResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "OrderResponseDto",
    description = """
        주문 응답 DTO
        
        * 주문 조회 결과를 담습니다.
        * 주문 정보, 주문자 정보, 메뉴 목록, 매장 정보를 포함합니다.
        """,
    example = """
        {
            "orderId": 1,
            "consumer": {
                "consumerId": 1,
                "name": "홍길동",
                "phone": "010-1234-5678"
            },
            "menus": [
                {
                    "menuId": 1,
                    "productId": 1,
                    "productName": "아메리카노"
                }
            ],
            "totalPrice": 4500,
            "orderStatus": "ORDERED",
            "storeId": 1,
            "storeName": "강남점"
        }
        """
)
public class OrderResponseDto {
    @Schema(description = "주문 번호", example = "1")
    private Long orderId;

    @Schema(description = "주문자 정보", example = """
        {
            "consumerId": 1,
            "name": "홍길동",
            "phone": "010-1234-5678"
        }
        """)
    private ConsumerResponseDto consumer;

    @Schema(description = "주문 메뉴 목록", example = """
        [
            {
                "menuId": 1,
                "productId": 1,
                "productName": "아메리카노"
            }
        ]
        """)
    private List<MenuResponseDto> menus;

    @Schema(description = "주문 총 가격", example = "4500")
    private Integer totalPrice;

    @Schema(description = "주문 상태", example = "ORDERED", allowableValues = {"ORDERED", "PREPARING", "READY", "COMPLETED", "CANCELLED"})
    private String orderStatus;

    @Schema(description = "매장 번호", example = "1")
    private Long storeId;

    @Schema(description = "매장명", example = "강남점")
    private String storeName;
}

