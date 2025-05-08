package com.cnco.campusflow.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Schema(
    name = "MenuProductResponseDto",
    description = """
        메뉴 상품 응답 DTO
        
        * 메뉴 상품의 기본 정보를 담습니다.
        * 상품 ID, 이름, 가격, 메인 이미지 URL을 포함합니다.
        """,
    example = """
        {
            "productId": 1,
            "productNm": "아메리카노",
            "price": 4500,
            "mainImageUrl": "https://example.com/images/americano.jpg"
        }
        """
)
public class MenuProductResponseDto {
    @Schema(description = "상품 번호", example = "1")
    private Long productId;

    @Schema(description = "상품명", example = "아메리카노")
    private String productNm;

    @Schema(description = "상품 가격", example = "4500")
    private Integer price;

    @Schema(description = "메인 이미지 URL", example = "https://example.com/images/americano.jpg")
    private String mainImageUrl;
}
