package com.cnco.campusflow.money;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(
    name = "AppUserMoneyUseRequestDto",
    description = """
        머니 사용 요청 DTO
        
        * 사용자의 머니를 사용할 때 사용됩니다.
        * 사용할 머니는 1원 이상이어야 합니다.
        * 매장 ID는 필수 입력 항목입니다.
        * 메모는 선택사항입니다.
        """
)
public class AppUserMoneyUseRequestDto {

    @NotNull(message = "매장 ID는 필수입니다")
    @Schema(
        description = """
            매장 ID
            * 머니를 사용할 매장의 ID
            * 필수 입력 항목
            """,
        example = "36"
    )
    private Long storeId;

    @NotNull(message = "사용할 머니는 필수입니다")
    @Min(value = 1, message = "사용할 머니는 1원 이상이어야 합니다")
    @Schema(
        description = """
            사용할 머니
            * 1원 이상의 정수값
            * 현재 보유한 머니보다 많이 사용할 수 없음
            * 필수 입력 항목
            """,
        example = "5000",
        minimum = "1"
    )
    private Integer amount;

    @Schema(
        description = """
            메모
            * 사용 사유나 관련 정보를 기록
            * 선택 입력 항목
            """,
        example = "커피 구매"
    )
    private String note;
} 