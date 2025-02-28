package com.cnco.campusflow.community;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommunityBoardRequestDto {

    private String title;
    private String content;
    private String secretYn;
    private String type;
}
