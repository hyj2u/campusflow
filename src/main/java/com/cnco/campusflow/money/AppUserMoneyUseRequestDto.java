package com.cnco.campusflow.money;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "머니 사용 요청")
public class AppUserMoneyUseRequestDto {

    @NotNull(message = "매장 ID는 필수입니다")
    @Schema(description = "매장 ID", example = "36")
    private Long storeId;

    @NotNull(message = "사용할 머니는 필수입니다")
    @Min(value = 1, message = "사용할 머니는 1원 이상이어야 합니다")
    @Schema(description = "사용할 머니", example = "5000")
    private Integer amount;

    @Schema(description = "메모", example = "커피 구매")
    private String note;
} 