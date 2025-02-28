package com.cnco.campusflow.community;

import com.cnco.campusflow.image.ImageResponseDto;
import com.cnco.campusflow.timetable.TimetableEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommunityBoardResponeDto {

    private Long boardId;
    private String title;
    private String content;
    private String secretYn;
    private String boardType;
    private String collegeName;
    private Integer collegeId;
    private Integer viewCnt;
    private Long appUserId;
    private String nickname;
    private LocalDateTime insertTimestamp;
    List<ImageResponseDto> images;

}
