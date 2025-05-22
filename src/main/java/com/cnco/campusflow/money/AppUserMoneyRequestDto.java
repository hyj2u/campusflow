package com.cnco.campusflow.money;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(
    name = "AppUserMoneyRequestDto",
    description = """
        머니 요청 DTO
        
        * 사용자의 머니 적립, 사용, 선물, 취소 시 사용됩니다.
        * 금액은 1원 이상이어야 합니다.
        * 메모는 선택사항입니다.
        * 매장 ID는 머니 사용 시에만 필수입니다.
        * 받는 사람 ID는 머니 선물 시에만 필수입니다.
        """
)
public class AppUserMoneyRequestDto {

    @NotNull(message = "금액은 필수입니다")
    @Min(value = 1, message = "금액은 1원 이상이어야 합니다")
    @Schema(
        description = """
            금액
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
            * 거래 사유나 관련 정보를 기록
            * 선택 입력 항목
            """,
        example = "시크릿머니 포인트 적립"
    )
    private String note;

    @Schema(
        description = """
            매장 ID
            * 머니 사용 시에만 필수
            * 다른 거래에서는 무시됨
            """,
        example = "1"
    )
    private Long storeId;

    @Schema(
        description = """
            받는 사람 ID
            * 머니 선물 시에만 필수
            * 다른 거래에서는 무시됨
            """,
        example = "1"
    )
    private Long appUserId;
} 