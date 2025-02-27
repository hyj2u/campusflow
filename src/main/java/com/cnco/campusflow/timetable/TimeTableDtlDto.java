package com.cnco.campusflow.timetable;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimeTableDtlDto {
    private Long tmTableId;
    private String tmTableName;
    private String category;
    private List<CourseDto> courses;
}
