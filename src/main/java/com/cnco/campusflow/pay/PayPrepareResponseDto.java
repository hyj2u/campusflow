package com.cnco.campusflow.pay;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
        name = "PayPrepareResponseDto",
        description = """
        KCP 결제창에 전달할 결제 준비 응답 DTO입니다.
        
        * 결제창 호출에 필요한 모든 필수 파라미터가 포함됩니다.
        * 각 값은 클라이언트가 form에 채워서 KCP로 submit 합니다.
        """,
        example = """
        {
            "siteCd": "T0000XXXX",
            "ordeId": "ORD202405240001",
            "goodName": "동국대 A관 5건 주문",
            "buyrName": "홍길동",
            "buyrTel": "01012345678",
            "buyrMail": "hong@example.com",
    
        }
        """
)
public class PayPrepareResponseDto {

    @Schema(description = "KCP 상점코드 (테스트: T0000...)", example = "T0000XXXXX")
    private String siteCd;

    @Schema(description = "주문번호", example = "ORD202405240001")
    private String orderId;

    @Schema(description = "상품명", example = "동국대 A관 5건 주문")
    private String goodName;


    @Schema(description = "구매자 이름", example = "홍길동")
    private String buyrName;

    @Schema(description = "구매자 전화번호", example = "01012345678")
    private String buyrTel;

    @Schema(description = "구매자 이메일", example = "user@example.com")
    private String buyrMail;

    @Schema(description = "금액", example = "5800")
    private String amount;
}

