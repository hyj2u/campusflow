package com.cnco.campusflow.coupon;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
    name = "AppUserCouponRegisterRequestDto",
    description = """
        쿠폰 등록 요청 DTO
        
        * 쿠폰 번호를 입력받아 등록합니다.
        * 쿠폰 번호는 'XXXX-XXXX-XXXX-XXXX' 형식입니다.
        * 등록 가능한 쿠폰은 activeYn='Y'이고 appUserId가 null인 경우입니다.
        * 만료일이 지나지 않은 쿠폰만 등록 가능합니다.
        * 이미 등록된 쿠폰은 재등록이 불가능합니다.
        """,
    example = """
        {
            "couponNumber": "AUB9-ES0E-OY6R-1RLP"
        }
        """
)
public class AppUserCouponRegisterRequestDto {
    @Schema(
        description = "쿠폰 번호",
        example = "AUB9-ES0E-OY6R-1RLP",
        pattern = "^[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}-[A-Z0-9]{4}$",
        required = true
    )
    private String couponNumber;
} 