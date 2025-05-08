package com.cnco.campusflow.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "FavoriteMenuResponseDto",
    description = """
        즐겨찾기 메뉴 응답 DTO
        
        * 사용자가 즐겨찾기한 메뉴 정보를 반환합니다.
        * 즐겨찾기 ID와 메뉴 상세 정보를 포함합니다.
        """,
    example = """
        {
            "favMenuId": 1,
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
public class FavoriteMenuResponseDto {
    @Schema(description = "즐겨찾기 메뉴 번호", example = "1")
    private Long favMenuId;

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
    private MenuResponseDto menu;
}

