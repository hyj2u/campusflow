package com.cnco.campusflow.eventboard;

import com.cnco.campusflow.image.ImageResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class EventBoardResponseDto {
    private Long boardId;
    private String title;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer viewCnt;
    private String pushYn;
    private String talkYn;
    private String fullExpYn;
    private Integer brandId;
    private String boardType;
    private String nickname;
    private Long appUserId;
    private List<ImageResponseDto> images;
    private String profileImgUrl;
    private List<Long> storeIds;
} 