package com.cnco.campusflow.gifticon;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
    name = "AppUserGifticonResponseDto",
    description = """
        기프티콘 응답 DTO
        
        * 기프티콘의 상세 정보를 포함합니다.
        * 상품 정보, 발신자/수신자 정보, 사용 여부 등을 포함합니다.
        * 구매(PURCHASE)와 선물(GIFT) 두 가지 타입이 있습니다.
        * 구매 시에는 수신자가 현재 사용자입니다.
        * 선물 시에는 발신자가 현재 사용자이고, 수신자는 전화번호로 찾은 사용자입니다.
        """,
    example = """
        {
            "appUserGifticonId": 1,
            "productId": 1,
            "productNm": "아메리카노",
            "storeNm": "강남점",
            "storeStatus": "O",
            "type": "GIFT",
            "senderId": 1,
            "senderNickname": "홍길동",
            "registerDate": "2024-03-20T14:30:00",
            "useYn": "N",
            "activeYn": "Y",
            "purchaseAmount": 5000
        }
        """
)
public class AppUserGifticonResponseDto {
    @Schema(description = "기프티콘 ID", example = "1")
    private Long appUserGifticonId;    // pkey

    @Schema(description = "등록 일시", example = "2024-03-20T14:30:00")
    private LocalDateTime registerDate; // 등록일시

    @Schema(
        description = """
            사용 여부
            * Y: 사용됨
            * N: 미사용
            """,
        example = "N",
        allowableValues = {"Y", "N"}
    )
    private String useYn;              // 사용 여부

    @Schema(description = "만료일", example = "2024-04-20T14:30:00")
    private LocalDateTime endDate;     // 만료일

    @Schema(
        description = """
            활성화 여부
            * Y: 활성화
            * N: 비활성화
            """,
        example = "Y",
        allowableValues = {"Y", "N"}
    )
    private String activeYn;           // 활성화 여부

    @Schema(description = "상품 ID", example = "1")
    private Long productId;            // 상품 ID

    @Schema(description = "상품명", example = "아메리카노")
    private String productNm;          // 상품명

    @Schema(description = "매장명", example = "강남점")
    private String storeNm;            // 매장명

    @Schema(
        description = """
            매장 상태
            * O: 영업중
            * W: 대기중
            * C: 폐점
            """,
        example = "O",
        allowableValues = {"O", "W", "C"}
    )
    private String storeStatus;        // 매장 상태

    @Schema(description = "선물한 사람 ID", example = "1")
    private Long senderId;             // 선물한 사람 ID

    @Schema(description = "선물한 사람 닉네임", example = "홍길동")
    private String senderNickname;     // 선물한 사람 닉네임

    @Schema(description = "구매 금액 (100원 단위로 올림)", example = "5000")
    private Long purchaseAmount;       // 구매금액
}