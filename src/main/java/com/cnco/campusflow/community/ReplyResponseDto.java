package com.cnco.campusflow.community;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ReplyResponseDto {

    private String content;
    private Long upTreeId;
    private Long appUserId;
    private Integer level;
    private String nickname;
    private LocalDateTime insertTimestamp;
    private Long replyId;
}
