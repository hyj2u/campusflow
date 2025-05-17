package com.cnco.campusflow.cscenter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CsCenterBoardRequestDto {
    private Long boardId;
    private String title;
    private String content;
    private String type;  // ACCOUNT_LOGIN, ACCOUNT_AUTH, ACCOUNT_EXIT, ACCOUNT_OTHER
} 