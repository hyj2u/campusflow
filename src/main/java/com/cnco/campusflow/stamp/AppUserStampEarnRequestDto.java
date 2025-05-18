package com.cnco.campusflow.stamp;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "스탬프 적립 요청")
public class AppUserStampEarnRequestDto {
    
    @NotNull(message = "적립할 스탬프 수는 필수입니다")
    @Min(value = 1, message = "적립할 스탬프 수는 1 이상이어야 합니다")
    @Schema(
        description = "적립할 스탬프 수",
        example = "5",
        minimum = "1",
        required = true
    )
    private Integer earnStampCount;

    @Schema(
        description = "스탬프 적립 메모",
        example = "커피 구매로 스탬프 적립",
        maxLength = 500
    )
    private String note;
} 