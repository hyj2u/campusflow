package com.cnco.campusflow.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "ConsumerAddressDto",
    description = """
        주문자 주소 DTO
        
        * 주문자의 배달 주소 정보를 담습니다.
        * 기본 주소, 상세 주소, 기본 배달지 여부를 포함합니다.
        """,
    example = """
        {
            "addressMain": "서울시 강남구",
            "addressDtl": "123-45",
            "defaultYn": "Y"
        }
        """
)
public class ConsumerAddressDto {
    @Schema(description = "배달 주소 번호", example = "1")
    private Long orderAddrId;

    @Schema(description = "기본 주소", example = "서울시 강남구")
    private String addressMain;

    @Schema(description = "상세 주소", example = "123-45")
    private String addressDtl;

    @Schema(description = "기본 배달지 여부", example = "Y", allowableValues = {"Y", "N"})
    private String defaultYn;
}

