package com.cnco.campusflow.pay;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "PayPrepareRequestDto",
    description = """
        결제준비 요청 DTO
        
        * 주문자의 주문 번호, 금액, 상품명을 받습니다.
        * 상품명은 동국대 A관 주문 이런식으로 사용자가 인지할 이름이기만 하면됨.
        """,
    example = """
        {
            "orderId": "3",
            "goodName": "동국대 A관 5건 주문",
            "amount": "5800"
        }
        """
)
public class PayPrepareRequestDto {
    @Schema(description = "주문 번호", example = "1")
    private Long orderId;

    @Schema(description = "주문명", example = "동국대 A관 5건 주문")
    private String goodName;

    @Schema(description = "금액", example = "5800")
    private String amount;

}

