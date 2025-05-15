package com.cnco.campusflow.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Schema(
        name = "MenuRequestDto",
        description = """
                메뉴 요청 DTO
                
                * 메뉴 생성을 위한 요청 데이터를 담습니다.
                * 상품 ID와 메뉴 옵션 목록을 포함합니다.
                """,
        example = """
                {
                    "productId": 60,
                    "options": [
                        {
                            "optionId": 129,
                            "optDtlId": 355,
                            "chosenNum": 2,
                            "totalPrice": 0
                        }
                    ]
                }
                """
)
public class MenuRequestDto {
    @NotNull(message = "상품 ID는 필수입니다")
    @Schema(description = "상품 번호", example = "1")
    private Long productId;

    @Schema(description = "메뉴 옵션 목록", example = """
            [
                {
                    "optionId": 1,
                    "optDtlId": 1,
                    "chosenNum": 2
                }
            ]
            """)
    private List<MenuOptionRequestDto> options;
}
