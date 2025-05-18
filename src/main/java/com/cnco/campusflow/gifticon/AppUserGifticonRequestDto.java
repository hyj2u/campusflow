package com.cnco.campusflow.gifticon;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Schema(
    name = "AppUserGifticonRequestDto",
    description = """
        기프티콘 생성 요청 DTO
        
        * 상품 ID와 구매 수량, 총 구매 금액은 필수 입력값입니다.
        * 선물할 경우 phoneNumbers 배열에 수신자 전화번호를 포함해야 합니다.
        * phoneNumbers의 개수는 구매 수량보다 클 수 없습니다.
        * 구매 금액은 수량으로 나누어 100원 단위로 올림하여 각 기프티콘에 할당됩니다.
        """,
    example = """
        {
            "productId": 1,
            "quantity": 3,
            "purchaseAmount": 10000,
            "phoneNumbers": ["010-1234-5678", "010-9876-5432"]
        }
        """
)
public class AppUserGifticonRequestDto {
    @NotNull(message = "상품 ID는 필수 입력값입니다.")
    @Schema(description = "상품 ID", example = "1")
    private Long productId;

    @NotNull(message = "구매 수량은 필수 입력값입니다.")
    @Min(value = 1, message = "구매 수량은 1 이상이어야 합니다.")
    @Schema(description = "구매 수량", example = "2", minimum = "1")
    private Integer quantity;

    @NotNull(message = "총 구매 금액은 필수 입력값입니다.")
    @Min(value = 1, message = "총 구매 금액은 1 이상이어야 합니다.")
    @Schema(description = "총 구매 금액", example = "10000", minimum = "1")
    private Long purchaseAmount;

    @Schema(
        description = """
            수신자 전화번호 목록
            * 선물할 경우에만 포함
            * 개수는 구매 수량보다 클 수 없음
            """,
        example = "[\"010-1234-5678\", \"010-9876-5432\"]"
    )
    private List<String> phoneNumbers;
} 