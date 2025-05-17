package com.cnco.campusflow.gifticon;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class GifticonResponseDto {
    private String gifticonName;    // 기프티콘 이름
    private LocalDateTime endDate;  // 만료일
    private String activeYn;        // 활성화 여부
    private String productNm;       // 상품명
    private String storeNm;         // 매장명
    private String storeStatus;     // 매장 상태
    private Long senderId;          // 선물한 사람 ID
    private String senderNickname;  // 선물한 사람 닉네임

    public GifticonResponseDto(String gifticonName, LocalDateTime endDate, String activeYn, 
                             String productNm, String storeNm, String storeStatus, 
                             Long senderId, String senderNickname) {
        this.gifticonName = gifticonName;
        this.endDate = endDate;
        this.activeYn = activeYn;
        this.productNm = productNm;
        this.storeNm = storeNm;
        this.storeStatus = storeStatus;
        this.senderId = senderId;
        this.senderNickname = senderNickname;
    }

    public static GifticonResponseDto from(GifticonEntity entity, AppUserGifticonEntity appUserGifticon) {
        return new GifticonResponseDto(
            entity.getGifticonName(),
            entity.getEndDate(),
            entity.getActiveYn(),
            entity.getProduct().getProductNm(),
            entity.getProduct().getStore().getStoreNm(),
            entity.getProduct().getStore().getStoreStatus(),
            appUserGifticon != null && appUserGifticon.getSender() != null ? appUserGifticon.getSender().getAppUserId() : null,
            appUserGifticon != null && appUserGifticon.getSender() != null ? appUserGifticon.getSender().getNickname() : null
        );
    }
} 