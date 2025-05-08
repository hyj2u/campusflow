package com.cnco.campusflow.timetable;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
    name = "TimeTableDtlDto",
    description = """
        시간표 상세 DTO
        
        * 시간표 상세 조회 시 사용되는 DTO입니다.
        * 시간표의 기본 정보와 강의 목록을 포함합니다.
        """,
    example = """
        {
            "tmTableId": 1,
            "tmTableName": "2024년 1학기 시간표",
            "category": "학부",
            "courses": [
                {
                    "courseId": 1,
                    "courseName": "자바 프로그래밍",
                    "professor": "홍길동",
                    "room": "A101"
                }
            ]
        }
        """
)
public class TimeTableDtlDto {
    @Schema(description = "시간표 번호", example = "1")
    private Long tmTableId;

    @Schema(description = "시간표명", example = "2024년 1학기 시간표")
    private String tmTableName;

    @Schema(description = "카테고리", example = "학부")
    private String category;

    @Schema(description = "강의 목록", example = """
        [
            {
                "courseId": 1,
                "courseName": "자바 프로그래밍",
                "professor": "홍길동",
                "room": "A101"
            }
        ]
        """)
    private List<CourseDto> courses;
}
