package com.cnco.campusflow.timetable;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeTableListDto {
    private Long tmTableId;
    private String tmTableName;

    private String category;
}
