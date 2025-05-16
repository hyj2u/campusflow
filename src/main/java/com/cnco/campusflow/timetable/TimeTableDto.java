package com.cnco.campusflow.timetable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(
    name = "TimeTableDto",
    description = """
        시간표 DTO
        
        * 시간표 생성/수정 시 사용되는 DTO입니다.
        * 시간표명과 카테고리 정보를 포함합니다.
        """,
    example = """
        {
            "tmTableName": "2024년 1학기 시간표",
            "category": "학부"
        }
        """
)
public class TimeTableDto {
    @Schema(description = "시간표명", example = "2024년 1학기 시간표")
    private String tmTableName;

    @Schema(description = "카테고리", example = "학부")
    private String category;
}
