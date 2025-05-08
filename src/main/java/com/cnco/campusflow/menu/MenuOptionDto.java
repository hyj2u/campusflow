package com.cnco.campusflow.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "MenuOptionDto",
    description = """
        메뉴 옵션 DTO
        
        * 메뉴 옵션의 상세 정보를 담습니다.
        * 옵션 기본 정보, 옵션 상세 정보, 가격 정보를 포함합니다.
        """,
    example = """
        {
            "menuOptId": 1,
            "optionId": 1,
            "optionNm": "샷 추가",
            "codeNm": "샷",
            "chosenNum": 2,
            "totalPrice": 1000,
            "optDtlId": 1,
            "optDtlNm": "에스프레소 샷",
            "min": 0,
            "max": 3,
            "price": 500,
            "type": "COUNT",
            "unitPrice": 500
        }
        """
)
public class MenuOptionDto {
    @Schema(description = "메뉴 옵션 번호", example = "1")
    private Long menuOptId;

    @Schema(description = "옵션 번호", example = "1")
    private Long optionId;

    @Schema(description = "옵션명", example = "샷 추가")
    private String optionNm;

    @Schema(description = "코드명", example = "샷")
    private String codeNm;

    @Schema(description = "선택된 옵션 수량", example = "2")
    private Integer chosenNum;

    @Schema(description = "옵션 총 가격", example = "1000")
    private Integer totalPrice;

    @Schema(description = "옵션 상세 번호", example = "1")
    private Long optDtlId;

    @Schema(description = "옵션 상세명", example = "에스프레소 샷")
    private String optDtlNm;

    @Schema(description = "최소 선택 수량", example = "0")
    private Integer min;

    @Schema(description = "최대 선택 수량", example = "3")
    private Integer max;

    @Schema(description = "옵션 가격", example = "500")
    private Integer price;

    @Schema(description = "옵션 타입", example = "COUNT")
    private String type;

    @Schema(description = "단위 가격", example = "500")
    private Integer unitPrice;
}

