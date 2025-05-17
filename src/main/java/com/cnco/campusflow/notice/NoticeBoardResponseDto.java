package com.cnco.campusflow.notice;

import com.cnco.campusflow.image.ImageResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NoticeBoardResponseDto {
    private Long boardId;
    private String title;
    private String content;
    private Integer viewCnt;
    private String pushYn;
    private String talkYn;
    private String boardType;
    private String nickname;
    private Long appUserId;
    private List<ImageResponseDto> images;
    private String profileImgUrl;
    private List<Long> storeIds;
} 