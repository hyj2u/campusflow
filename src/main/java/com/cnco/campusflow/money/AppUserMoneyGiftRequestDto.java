package com.cnco.campusflow.money;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(
    name = "AppUserMoneyGiftRequestDto",
    description = """
        머니 선물 요청 DTO
        
        * 다른 사용자에게 머니를 선물할 때 사용됩니다.
        * 선물할 머니는 1원 이상이어야 합니다.
        * 받는 사람의 ID는 필수 입력 항목입니다.
        * 자기 자신에게는 선물할 수 없습니다.
        * 메모는 선택사항입니다.
        """
)
public class AppUserMoneyGiftRequestDto {

    @NotNull(message = "받는 사람의 ID는 필수입니다")
    @Schema(
        description = """
            받는 사람의 ID
            * 선물을 받을 사용자의 ID
            * 자기 자신의 ID는 사용할 수 없음
            * 필수 입력 항목
            """,
        example = "1"
    )
    private Long appUserId;

    @NotNull(message = "선물할 금액은 필수입니다")
    @Min(value = 1, message = "선물할 금액은 1원 이상이어야 합니다")
    @Schema(
        description = """
            선물할 금액
            * 1원 이상의 정수값
            * 현재 보유한 머니보다 많이 선물할 수 없음
            * 필수 입력 항목
            """,
        example = "1000",
        minimum = "1"
    )
    private Long amount;

    @Schema(
        description = """
            선물 메모
            * 선물 사유나 관련 정보를 기록
            * 선택 입력 항목
            """,
        example = "생일 축하해요!"
    )
    private String note;
} 