package com.cnco.campusflow.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "ConsumerResponseDto",
    description = """
        주문자 응답 DTO
        
        * 주문자 정보 조회 결과를 담습니다.
        * 주문자 ID, 배달 주소 정보, 요청사항, 연락처를 포함합니다.
        """,
    example = """
        {
            "consumerId": 1,
            "orderAddrId": 1,
            "addressMain": "서울시 강남구",
            "addressDtl": "123-45",
            "defaultYn": "Y",
            "storeDemand": "포크 2개 주세요",
            "deliveryDemand": "문 앞에 놓아주세요",
            "phone": "010-1234-5678"
        }
        """
)
public class ConsumerResponseDto {
    @Schema(description = "주문자 번호", example = "1")
    private Long consumerId;

    @Schema(description = "배달 주소 번호", example = "1")
    private Long orderAddrId;

    @Schema(description = "기본 주소", example = "서울시 강남구")
    private String addressMain;

    @Schema(description = "상세 주소", example = "123-45")
    private String addressDtl;

    @Schema(description = "기본 배달지 여부", example = "Y", allowableValues = {"Y", "N"})
    private String defaultYn;

    @Schema(description = "매장 요청사항", example = "포크 2개 주세요")
    private String storeDemand;

    @Schema(description = "배달 요청사항", example = "문 앞에 놓아주세요")
    private String deliveryDemand;

    @Schema(description = "연락처", example = "010-1234-5678")
    private String phone;
}

