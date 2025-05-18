package com.cnco.campusflow.point;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(
    name = "AppUserPointEarnRequestDto",
    description = "포인트 적립 요청",
    example = """
        {
            "earnPointCount": 100,
            "note": "커피 구매로 포인트 적립"
        }
        """
)
public class AppUserPointEarnRequestDto {
    
    @NotNull(message = "적립할 포인트 수는 필수입니다")
    @Min(value = 1, message = "적립할 포인트 수는 1 이상이어야 합니다")
    @Schema(
        description = "적립할 포인트 수",
        example = "100",
        minimum = "1",
        required = true,
        type = "integer",
        format = "int32"
    )
    private Integer earnPointCount;

    @Schema(
        description = "포인트 적립 메모",
        example = "커피 구매로 포인트 적립",
        maxLength = 500,
        type = "string",
        nullable = true
    )
    private String note;
} 