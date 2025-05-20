package com.cnco.campusflow.coupon;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
    name = "AppUserCouponResponseDto",
    description = """
        쿠폰 응답 DTO
        
        * 쿠폰의 상세 정보를 포함합니다.
        * 쿠폰 금액, 만료일, 사용 여부 등을 포함합니다.
        * 사용 가능한 쿠폰만 조회됩니다 (activeYn='Y', useYn='N', endDate >= now())
        * 만료일 기준 오름차순 정렬됩니다.
        """,
    example = """
        {
            "appUserCouponId": 1,
            "couponId": 1,
            "couponGenId": null,
            "couponNumber": "AUB9-ES0E-OY6R-1RLP",
            "couponName": "신규 가입 쿠폰",
            "couponAmount": 5000,
            "endDate": "2024-12-31T23:59:59",
            "useYn": "N",
            "activeYn": "Y",
            "registerDate": "2024-03-20T14:30:00"
        }
        """
)
public class AppUserCouponResponseDto {
    @Schema(description = "사용자 쿠폰 ID", example = "1")
    private Long appUserCouponId;

    @Schema(description = "쿠폰 ID", example = "1")
    private Long couponId;

    @Schema(description = "쿠폰 생성 ID", example = "1")
    private Long couponGenId;

    @Schema(description = "쿠폰 번호", example = "AUB9-ES0E-OY6R-1RLP")
    private String couponNumber;

    @Schema(description = "쿠폰 이름", example = "신규 가입 쿠폰")
    private String couponName;

    @Schema(description = "쿠폰 금액", example = "5000")
    private Integer couponAmount;

    @Schema(description = "만료일", example = "2024-12-31T23:59:59")
    private LocalDateTime endDate;

    @Schema(description = "사용 여부 (Y: 사용, N: 미사용)", example = "N")
    private String useYn;

    @Schema(description = "활성화 여부 (Y: 활성화, N: 비활성화)", example = "Y")
    private String activeYn;

    @Schema(description = "등록일시", example = "2024-03-20T14:30:00")
    private LocalDateTime registerDate;
}