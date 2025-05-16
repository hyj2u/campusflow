package com.cnco.campusflow.timetable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
    name = "CourseDto",
    description = """
        강의 DTO
        
        * 강의 생성/수정 시 사용되는 DTO입니다.
        * 강의의 기본 정보(번호, 이름, 요일, 장소, 시간, 교수, 색상)와 시간표 번호를 포함합니다.
        * coudseId 가 있으면 생성, 없으면 수정
        """,
    example = """
        {
           
            "courseName": "자바 프로그래밍",
            "days": ["MON", "WED"],
            "location": "A101",
            "startTime": "09:00",
            "endTime": "10:30",
            "professor": "홍길동",
            "color": "#FF0000",
            "timeTableId": 1
        }
        """
)
public class CourseDto {
    @Schema(description = "강의 번호", example = "1")
    private Long courseId;

    @Schema(description = "강의명", example = "자바 프로그래밍")
    private String courseName;

    @Schema(description = "강의 요일 목록", example = "[\"MON\", \"WED\"]")
    private List<String> days;

    @Schema(description = "강의실", example = "A101")
    private String location;

    @Schema(description = "시작 시간", example = "09:00")
    private String startTime;

    @Schema(description = "종료 시간", example = "10:30")
    private String endTime;

    @Schema(description = "교수명", example = "홍길동")
    private String professor;

    @Schema(description = "색상 코드", example = "#FF0000")
    private String color;

    @Schema(description = "시간표 번호", example = "1")
    private Long timeTableId;
}
