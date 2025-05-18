package com.cnco.campusflow.cscenter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CsCenterReplyResponseDto {
    private String content;
    private Long upTreeId;
    private Long appUserId;
    private Integer level;
    private String nickname;
    private LocalDateTime insertTimestamp;
    private Long replyId;
    private String deleteYn;
    private String blindYn;
    private String helpfulYn;
} 