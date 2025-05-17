package com.cnco.campusflow.oftenqna;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OftenQnaResponseDto {
    private Long boardId;
    private String title;
    private String content;
    private Integer viewCnt;
    private String category;
} 