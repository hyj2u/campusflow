package com.cnco.campusflow.gifticon;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppUserGifticonResponseDto {
    private Long appUserGifticonId;    // pkey
    private LocalDateTime registerDate; // 등록일시
    private String useYn;              // 사용 여부
    private LocalDateTime endDate;  // 만료일
    private String activeYn;        // 활성화 여부
    private Long productId;       // 상품 ID
    private String productNm;       // 상품명
    private String storeNm;         // 매장명
    private String storeStatus;     // 매장 상태
    private Long senderId;          // 선물한 사람 ID
    private String senderNickname;  // 선물한 사람 닉네임
}