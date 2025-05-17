package com.cnco.campusflow.gifticon;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppUserGifticonResponseDto {
    private Long appUserGifticonId;    // 기프티콘 회원 ID
    private String phone;              // 연락처
    private LocalDateTime registerDate; // 등록일시
    private String useYn;              // 사용 여부
    private String activeYn;           // 활성화 여부
    
    // 앱 회원 정보
    private Long appUserId;            // 앱 회원 ID
    private String appUserName;        // 앱 회원 이름
        
    // 발송 정보
    private Long sendInfoId;           // 발송 정보 ID
    private String sendStatus;         // 발송 상태
    private String sendMessage;        // 발송 메시지
    private LocalDateTime sendTm;      // 발송 시간
    
    private LocalDateTime insertTimestamp;
    private LocalDateTime updateTimestamp;
}