package com.cnco.campusflow.money;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "머니 충전 요청 DTO")
public class AppUserMoneyChargeRequestDto {

    @NotNull(message = "충전 금액은 필수입니다")
    @Min(value = 1, message = "충전 금액은 1원 이상이어야 합니다")
    @Schema(description = "충전할 금액", example = "10000")
    private Integer chargeAmount;

    @Schema(description = "충전 메모", example = "신용카드 충전")
    private String note;
} 