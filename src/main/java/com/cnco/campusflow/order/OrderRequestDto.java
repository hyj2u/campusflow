package com.cnco.campusflow.order;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Schema(
    name = "OrderRequestDto",
    description = """
        주문 요청 DTO
        
        * 주문 생성을 위한 요청 데이터를 담습니다.
        * 주문자 ID, 메뉴 ID 목록, 총 가격을 포함합니다.
        """,
    example = """
        {
            "consumerId": 1,
            "menuIds": [1, 2, 3],
            "totalPrice": 15000
        }
        """
)
public class OrderRequestDto {
    @NotNull(message = "주문자 ID는 필수입니다")
    @Schema(description = "주문자 번호", example = "1")
    private Long consumerId;

    @NotEmpty(message = "메뉴 ID 목록은 필수입니다")
    @Schema(description = "주문 메뉴 번호 목록", example = "[1, 2, 3]")
    private List<Long> menuIds;

    @NotNull(message = "총 가격은 필수입니다")
    @Positive(message = "총 가격은 0보다 커야 합니다")
    @Schema(description = "주문 총 가격", example = "15000")
    private Integer totalPrice;
}

