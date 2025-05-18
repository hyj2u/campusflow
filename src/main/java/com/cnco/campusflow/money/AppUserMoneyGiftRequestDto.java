package com.cnco.campusflow.money;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "머니 선물 요청")
public class AppUserMoneyGiftRequestDto {

    @NotNull(message = "받는 사람의 ID는 필수입니다")
    @Schema(description = "받는 사람의 ID", example = "1")
    private Long appUserId;

    @NotNull(message = "선물할 금액은 필수입니다")
    @Min(value = 1, message = "선물할 금액은 1원 이상이어야 합니다")
    @Schema(description = "선물할 금액", example = "1000")
    private Long amount;

    @Schema(description = "선물 메모", example = "생일 축하해요!")
    private String note;
} 