package com.cnco.campusflow.cscenter;

import com.cnco.campusflow.image.ImageResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CsCenterBoardResponseDto {
    private Long boardId;
    private String title;
    private String content;
    private Integer viewCnt;
    private String boardType;
    private String nickname;
    private Long appUserId;
    private List<ImageResponseDto> images;
    private String profileImgUrl;
    private LocalDateTime insertTimestamp;
    private List<CsCenterReplyResponseDto> replies;
} 