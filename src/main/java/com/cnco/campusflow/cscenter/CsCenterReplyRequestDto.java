package com.cnco.campusflow.cscenter;

import lombok.Data;

@Data
public class CsCenterReplyRequestDto {
    private Long replyId;
    private String content;
    private Long upTreeId;
} 