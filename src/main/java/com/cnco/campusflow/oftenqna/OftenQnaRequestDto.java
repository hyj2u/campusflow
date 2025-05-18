package com.cnco.campusflow.oftenqna;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(
    name = "OftenQnaRequestDto",
    description = """
        자주 묻는 질문 요청 DTO
        
        * 자주 묻는 질문 생성에 필요한 정보를 포함합니다.
        * 제목, 내용, 카테고리 정보가 필요합니다.
        * 카테고리는 PAYMENT, DELIVERY, PRODUCT, ETC 중 하나여야 합니다.
        """,
    example = """
        {
            "title": "결제 방법은 어떻게 되나요?",
            "content": "신용카드, 계좌이체, 휴대폰 결제 등 다양한 결제 방법을 지원합니다.",
            "category": "PAYMENT"
        }
        """
)
public class OftenQnaRequestDto {
    @Schema(description = "제목", example = "결제 방법은 어떻게 되나요?")
    private String title;

    @Schema(description = "내용", example = "신용카드, 계좌이체, 휴대폰 결제 등 다양한 결제 방법을 지원합니다.")
    private String content;

    @Schema(
        description = """
            카테고리
            * PAYMENT: 결제
            * DELIVERY: 배송
            * PRODUCT: 상품
            * ETC: 기타
            """,
        example = "PAYMENT",
        allowableValues = {"PAYMENT", "DELIVERY", "PRODUCT", "ETC"}
    )
    private String category;
} 