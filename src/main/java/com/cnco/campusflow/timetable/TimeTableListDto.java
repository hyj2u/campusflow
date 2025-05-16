package com.cnco.campusflow.timetable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
    name = "TimeTableListDto",
    description = """
        시간표 목록 DTO
        
        * 시간표 목록 조회 시 사용되는 DTO입니다.
        * 시간표의 기본 정보(번호, 이름, 카테고리)를 포함합니다.
        """,
    example = """
        {
            "tmTableId": 1,
            "tmTableName": "2024년 1학기 시간표",
            "category": "학부"
        }
        """
)
public class TimeTableListDto {
    @Schema(description = "시간표 번호", example = "1")
    private Long tmTableId;

    @Schema(description = "시간표명", example = "2024년 1학기 시간표")
    private String tmTableName;

    @Schema(description = "카테고리", example = "학부")
    private String category;
}
