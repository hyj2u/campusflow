package com.cnco.campusflow.coupon;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
    name = "AppUserCouponUseRequestDto",
    description = """
        쿠폰 사용 요청 DTO
        
        * 쿠폰 사용 시 주문 금액을 입력받습니다.
        * 주문 금액이 쿠폰 금액 이상인 경우에만 사용 가능합니다.
        * 쿠폰 사용 시 useYn이 'Y'로 변경되고 updateTimestamp가 현재 시간으로 설정됩니다.
        """,
    example = """
        {
            "orderAmount": 10000
        }
        """
)
public class AppUserCouponUseRequestDto {
    @Schema(
        description = "주문 금액",
        example = "10000",
        minimum = "0"
    )
    private Long orderAmount;
}