package com.cnco.campusflow.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "ConsumerRequestDto",
    description = """
        주문자 요청 DTO
        
        * 주문자 정보 생성을 위한 요청 데이터를 담습니다.
        * 주문자 ID, 배달 주소 ID, 매장 요청사항, 배달 요청사항을 포함합니다.
        """,
    example = """
        {
            "consumerId": 1,
            "orderAddrId": 4,
            "storeDemand": "포크 2개 주세요",
            "deliveryDemand": "문 앞에 놓아주세요"
        }
        """
)
public class ConsumerRequestDto {
    @NotNull(message = "주문자 ID는 필수입니다")
    @Schema(description = "주문자 번호", example = "1")
    private Long consumerId;

    @NotNull(message = "배달 주소 ID는 필수입니다")
    @Schema(description = "배달 주소 번호", example = "1")
    private Long orderAddrId;

    @Schema(description = "매장 요청사항", example = "포크 2개 주세요")
    private String storeDemand;

    @Schema(description = "배달 요청사항", example = "문 앞에 놓아주세요")
    private String deliveryDemand;
}

