package com.cnco.campusflow.menu;

import com.cnco.campusflow.common.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "MenuOptionRequestDto",
    description = """
        메뉴 옵션 요청 DTO
        
        * 메뉴 옵션 생성을 위한 요청 데이터를 담습니다.
        * 옵션 ID, 옵션 상세 ID, 선택 수량을 포함합니다.
        """,
    example = """
        {
            "optionId": 1,
            "optDtlId": 1,
            "chosenNum": 2,
            "totalPrice": 1000
        }
        """
)
public class MenuOptionRequestDto extends BaseEntity {
    @NotNull(message = "옵션 ID는 필수입니다")
    @Schema(description = "옵션 번호", example = "1")
    private Long optionId;

    @NotNull(message = "옵션 상세 ID는 필수입니다")
    @Schema(description = "옵션 상세 번호", example = "1")
    private Long optDtlId;

    @NotNull(message = "선택 수량은 필수입니다")
    @Positive(message = "선택 수량은 1 이상이어야 합니다")
    @Schema(description = "선택된 옵션 수량", example = "2")
    private Integer chosenNum;

    @Schema(description = "옵션 총 가격", example = "1000")
    private Integer totalPrice;
}

