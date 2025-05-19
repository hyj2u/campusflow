package com.cnco.campusflow.point;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "포인트 요청")
public class AppUserPointRequestDto {
    
    @Schema(
        description = """
            포인트 금액
            * 1 이상의 값이어야 합니다.
            * 적립 시: 양수 값
            * 사용 시: 현재 보유 포인트보다 많은 포인트를 사용할 수 없습니다.
            """,
        example = "100",
        minimum = "1"
    )
    private Integer amount;

    @Schema(
        description = "포인트 메모",
        example = "커피 구매"
    )
    private String note;
} 