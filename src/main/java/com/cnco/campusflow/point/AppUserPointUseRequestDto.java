package com.cnco.campusflow.point;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(
    name = "AppUserPointUseRequestDto",
    description = "포인트 사용 요청",
    example = """
        {
            "usePointCount": 500,
            "note": "아메리카노 구매 시 포인트 사용"
        }
        """
)
public class AppUserPointUseRequestDto {
    
    @NotNull(message = "사용할 포인트 수는 필수입니다")
    @Min(value = 1, message = "사용할 포인트 수는 1 이상이어야 합니다")
    @Schema(
        description = "사용할 포인트 수",
        example = "500",
        minimum = "1",
        required = true,
        type = "integer",
        format = "int32"
    )
    private Integer usePointCount;

    @Schema(
        description = "포인트 사용 메모",
        example = "아메리카노 구매 시 포인트 사용",
        maxLength = 500,
        type = "string",
        nullable = true
    )
    private String note;
} 