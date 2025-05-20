package com.cnco.campusflow.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(
        name = "CategoryMenuResponseDto",
        description = """
        카테고리별 메뉴 응답 DTO
        
        * 한 카테고리에 포함된 메뉴 상품들을 담는 응답 구조입니다.
        * 브랜드 정보, 카테고리 정보, 메뉴 리스트를 포함합니다.
        """,
        example = """
        {
            "categoryId": 10,
            "categoryNm": "커피",
            "orderNum": 1,
            "brandNm": "카페24",
            "brandId": 2,
            "menuList": [
                {
                    "productId": 101,
                    "productNm": "아메리카노",
                    "price": 3000,
                    "mainImageUrl": "https://cdn.example.com/images/101.png",
                    "activeYn": "Y",
                    "productEngNm": "Americano"
                },
                {
                    "productId": 102,
                    "productNm": "카페라떼",
                    "price": 3500,
                    "mainImageUrl": "https://cdn.example.com/images/102.png",
                    "activeYn": "Y",
                    "productEngNm": "Cafe Latte"
                }
            ]
        }
        """
)
public class CategoryMenuResponseDto {

    @Schema(description = "카테고리 ID", example = "10")
    private Long categoryId;

    @Schema(description = "카테고리 이름", example = "커피")
    private String categoryNm;

    @Schema(description = "카테고리 정렬 순서", example = "1")
    private Integer orderNum;

    @Schema(description = "브랜드 이름", example = "카페24")
    private String brandNm;

    @Schema(description = "브랜드 ID", example = "2")
    private Long brandId;

    @Schema(description = "카테고리에 포함된 메뉴 리스트")
    private List<MenuProductResponseDto> menuList;
}
