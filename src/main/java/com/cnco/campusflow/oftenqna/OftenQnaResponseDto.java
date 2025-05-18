package com.cnco.campusflow.oftenqna;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(
    name = "OftenQnaResponseDto",
    description = """
        자주 묻는 질문 응답 DTO
        
        * 자주 묻는 질문의 상세 정보를 포함합니다.
        * 제목, 내용, 조회수, 카테고리 등의 기본 정보를 포함합니다.
        * 카테고리는 PAYMENT, DELIVERY, PRODUCT, ETC 등이 있습니다.
        """,
    example = """
        {
            "boardId": 1,
            "title": "결제 방법은 어떻게 되나요?",
            "content": "신용카드, 계좌이체, 휴대폰 결제 등 다양한 결제 방법을 지원합니다.",
            "viewCnt": 150,
            "category": "PAYMENT"
        }
        """
)
public class OftenQnaResponseDto {
    @Schema(description = "게시글 ID", example = "1")
    private Long boardId;

    @Schema(description = "제목", example = "결제 방법은 어떻게 되나요?")
    private String title;

    @Schema(description = "내용", example = "신용카드, 계좌이체, 휴대폰 결제 등 다양한 결제 방법을 지원합니다.")
    private String content;

    @Schema(description = "조회수", example = "150")
    private Integer viewCnt;

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