package com.cnco.campusflow.news;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(
    name = "NewsReportRequestDto",
    description = """
        뉴스 신고 요청 DTO
        
        * 뉴스 신고 시 필요한 정보를 담습니다.
        * 뉴스 ID와 신고 사유는 필수 입력 항목입니다.
        """,
    example = """
        {
            "newsId": 1,
            "activeYn": "N",
            "reportReason": "부적절한 내용",
            "reportYn": "Y"
        }
        """
)
public class NewsReportRequestDto {
    @NotNull(message = "뉴스 ID는 필수 입력 항목입니다")
    @Schema(description = "뉴스 ID", example = "1", required = true)
    private Long newsId;

    @Schema(description = "활성화 여부", example = "N", allowableValues = {"Y", "N"})
    private String activeYn;

    @NotBlank(message = "신고 사유는 필수 입력 항목입니다")
    @Schema(description = "신고 사유", example = "부적절한 내용", required = true)
    private String reportReason;

    @Schema(description = "신고 여부", example = "Y", allowableValues = {"Y", "N"})
    private String reportYn;
} 