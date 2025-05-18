package com.cnco.campusflow.money;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "머니 적립 요청")
public class AppUserMoneyEarnRequestDto {

    @NotNull(message = "적립할 머니는 필수입니다")
    @Min(value = 1, message = "적립할 머니는 1원 이상이어야 합니다")
    @Schema(description = "적립할 머니", example = "5000")
    private Integer amount;

    @Schema(description = "메모", example = "시크릿머니 포인트 구매")
    private String note;
} 