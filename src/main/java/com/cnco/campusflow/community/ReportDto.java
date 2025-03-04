package com.cnco.campusflow.community;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportDto {
    private Long boardId;
    private String reason;

}
