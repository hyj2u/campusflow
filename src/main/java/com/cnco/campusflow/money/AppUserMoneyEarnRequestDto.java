package com.cnco.campusflow.money;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(
    name = "AppUserMoneyEarnRequestDto",
    description = """
        머니 적립 요청 DTO
        
        * 사용자의 머니를 적립할 때 사용됩니다.
        * 적립할 머니는 1원 이상이어야 합니다.
        * 메모는 선택사항입니다.
        """
)
public class AppUserMoneyEarnRequestDto {

    @NotNull(message = "적립할 머니는 필수입니다")
    @Min(value = 1, message = "적립할 머니는 1원 이상이어야 합니다")
    @Schema(
        description = """
            적립할 머니
            * 1원 이상의 정수값
            * 필수 입력 항목
            """,
        example = "5000",
        minimum = "1"
    )
    private Integer amount;

    @Schema(
        description = """
            메모
            * 적립 사유나 관련 정보를 기록
            * 선택 입력 항목
            """,
        example = "시크릿머니 포인트 구매"
    )
    private String note;
} 