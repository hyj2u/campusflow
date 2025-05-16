package com.cnco.campusflow.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "MenuResponseDto",
    description = """
        메뉴 응답 DTO
        
        * 메뉴 조회 결과를 담습니다.
        * 매장 정보, 상품 정보, 메뉴 옵션 목록을 포함합니다.
        """,
    example = """
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
        """
)
public class MenuResponseDto {
    @Schema(description = "메뉴 번호", example = "1")
    private Long menuId;
    @Schema(description = "주문 수량", example = "1")
    private Integer orderCnt;

    @Schema(description = "매장 번호", example = "1")
    private Long storeId;

    @Schema(description = "매장명", example = "강남점")
    private String storeName;

    @Schema(description = "상품 번호", example = "1")
    private Long productId;

    @Schema(description = "상품명", example = "아메리카노")
    private String productName;

    @Schema(description = "메뉴 옵션 목록", example = """
        [
            {
                "optionId": 1,
                "optionName": "샷 추가",
                "optDtlId": 1,
                "optDtlName": "에스프레소 샷",
                "chosenNum": 2,
                "totalPrice": 1000
            }
        ]
        """)
    private List<MenuOptionDto> options;
}

