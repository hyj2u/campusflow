package com.cnco.campusflow.menu;

import com.cnco.campusflow.optgrp.OptGrpResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Schema(
        name = "MenuProductResponseDto",
        description = """
                메뉴 상품 상세 응답 DTO
                
                * 메뉴 상품의 기본 정보와 상세를 담습니다.
                * 상품 ID, 이름, 가격, 메인 이미지 URL을 포함합니다.
                * 옵션정보
                """,
        example = """
                    {
                        "data": {
                            "productId": 60,
                            "productNm": "카페라떼",
                            "price": 4500,
                            "mainImageUrl": null,
                            "optGrps": [
                                {
                                    "optGrpId": 128,
                                    "optGrpNm": "토핑 선택",
                                    "orderNum": "4",
                                    "options": [
                                        {
                                            "optionId": 128,
                                            "optionNm": "Toppings",
                                            "codeNm": "스와이프",
                                            "requireYn": "Y",
                                            "details": [
                                                {
                                                    "optDtlId": 352,
                                                    "opDtlNm": "Whipped Cream",
                                                    "min": 0,
                                                    "max": 1,
                                                    "price": 500,
                                                    "dtlUseYn": "Y"
                                                }
                                            ]
                                        }
                                    ]
                                }
                            ]
                        }
                    }
                """
)
public class MenuProductDtlResponseDto {
    @Schema(description = "상품 번호", example = "1")
    private Long productId;

    @Schema(description = "상품명", example = "아메리카노")
    private String productNm;

    @Schema(description = "상품 가격", example = "4500")
    private Integer price;

    @Schema(description = "메인 이미지 URL", example = "https://example.com/images/americano.jpg")
    private String mainImageUrl;
    @Schema(description = "옵션정보", example = """
            [
                    {
                        "optGrpId": 129,
                        "optGrpNm": "토핑 선택",
                        "orderNum": "4",
                        "options": [
                            {
                                "optionId": 129,
                                "optionNm": "Milk Type",
                                "codeNm": "QA_BOARD",
                                "requireYn": "Y",
                                "details": [
                                    {
                                        "optDtlId": 355,
                                        "opDtlNm": "Whole Milk",
                                        "min": 1,
                                        "max": 1,
                                        "price": 0,
                                        "dtlUseYn": "Y"
                                    }
                            }
                        ]
            """)
    private List<OptGrpResponseDto> optGrps;
}
