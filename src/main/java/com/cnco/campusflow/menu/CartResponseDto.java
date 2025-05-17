package com.cnco.campusflow.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "CartResponseDto",
    description = """
        장바구니 조회 응답 DTO
        
        * 사용자가 장바구니에 담은 메뉴 정보를 반환합니다.
        """,
    example = """
        {
            "cartId": 1,
            "menu": {
                "menuId": 1,
                "storeId": 1,
                "storeName": "강남점",
                "productId": 1,
                "productName": "아메리카노",
                "options": [
                    {
                        "optionId": 1,
                        "optionName": "샷 추가",
                        "optDtlId": 1,
                        "optDtlName": "에스프레소 샷",
                        "chosenNum": 2,
                        "totalPrice": 1000
                    }
                ]
            }
        }
        """
)
public class CartResponseDto {
    @Schema(description = "장바구니 번호", example = "1")
    private Long cartId;

    @Schema(description = "메뉴 상세 정보", example = """
        {
            "menuId": 1,
            "storeId": 1,
            "storeName": "강남점",
            "productId": 1,
            "productName": "아메리카노",
            "options": [
                {
                    "optionId": 1,
                    "optionName": "샷 추가",
                    "optDtlId": 1,
                    "optDtlName": "에스프레소 샷",
                    "chosenNum": 2,
                    "totalPrice": 1000
                }
            ]
        }
        """)
    private List<MenuResponseDto> menus;
}

