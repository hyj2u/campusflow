package com.cnco.campusflow.timetable;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDto {

    private Long courseId;
    private String courseName;
    private List<Long> days;
    private String location;
    private String startTime;
    private String endTime;
    private String professor;
    private String color;
    private Long timeTableId;
}
